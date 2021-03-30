package model;


import model.war.Clazz;
import model.war.Player;
import model.war.Warrior;
import org.sqlite.JDBC;
import app.ApplicationProperties;

import java.sql.*;

import static model.war.Warrior.*;

public class DAO {

    //https://habr.com/en/sandbox/88039/
    //https://alekseygulynin.ru/rabota-s-sqlite-v-java/

    private static String LOGIN = "login";
    private static String PASSWORD = "password";
    private static String ID = "id";
    private static String TABLE_NAME = "players";

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resSet;
    private StringBuilder request;

    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public synchronized void createConnection() throws SQLException {
        if (connection == null) {
            DriverManager.registerDriver(new JDBC());
            connection = DriverManager.getConnection("jdbc:sqlite:" + ApplicationProperties.getProperties("db_path"));
            statement = connection.createStatement();
            System.out.println("База Подключена!");
        }
    }

    public DAO() throws SQLException {
        createConnection();
        dropTable(TABLE_NAME);
        createDB();
        initDB();
        readDB();
    }

    public void closeDB() {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (statement != null)
                statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (resSet != null)
                resSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Соединения закрыты, база отключена");
    }

    public void dropTable(String name) throws SQLException {
        statement.execute("DROP TABLE if exists '" + name + "';");
    }

    public void createDB() throws SQLException {
        statement.execute("CREATE TABLE if not exists '" + TABLE_NAME + "' (" +
                "'" + ID + "' INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "'" + LOGIN + "' text, " +
                "'" + PASSWORD + "' text, " +
                "'" + EXP + "' INT, " +
                "'" + HP + "' INT, " +
                "'" + ATTACK + "' INT, " +
                "'" + DEFENSE + "' INT, " +
                "'" + HELMET + "' INT, " +
                "'" + TYPE + "' text, " +
                "'" + LEVEL + "' INT);"
        );
        System.out.println("Таблица создана или уже существует.");
    }

    // --------Заполнение таблицы--------
    public void initDB() {
        Player player = new Player("Capybara");
        player.setExperience(0);
        player.setLevel(5);
        createPlayer(player.getName(), "_" + player.getName() + "_", player);

        player = new Player("Name2");
        createPlayer(player.getName(), "_" + player.getName() + "_", player);

        player = new Player("Name3");
        createPlayer(player.getName(), "_" + player.getName() + "_", player);

        System.out.println("Таблица заполнена");
    }

    // -------- Вывод таблицы--------
    public void readDB() throws SQLException {
        resSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
        while (resSet.next()) {
            int id = resSet.getInt(ID);
            String login = resSet.getString(LOGIN);
            String password = resSet.getString(PASSWORD);
            int exp = resSet.getInt(EXP);
            int hp = resSet.getInt(HP);
            int attack = resSet.getInt(ATTACK);
            int def = resSet.getInt(DEFENSE);
            int helmet = resSet.getInt(HELMET);
            int level = resSet.getInt(LEVEL);
            String type = resSet.getString(TYPE);
            System.out.printf("ID = %d, login = %s, pas = %s, exp = %d, hp = %d, attack = %d, def = %d, helmet = %d, level = %d, type = %s",
                    id, login, password, exp, hp, attack, def, helmet, level, type);
            System.out.println();
        }

        System.out.println("Таблица выведена");
    }

    private void insertRequest(String field, String value) {
        request = new StringBuilder("INSERT INTO '").append(TABLE_NAME).append("' ( '")
                .append(field)
                .append("' ) VALUES ( '")
                .append(value)
                .append("' );");
    }

    private void insertInRequest(String field, String value) {
        int posField = request.indexOf(")") - 1;
        int posValue = request.length() - 3;
        request.insert(posValue, value);
        request.insert(posField, field);
    }

    private void addArg(String field, String value) {
        insertInRequest(", '", ", '");
        insertInRequest(field, value);
        insertInRequest("'", "'");
    }

    private void addArg(String field, int value) {
        insertInRequest(", '", ", ");
        insertInRequest(field, String.valueOf(value));
        insertInRequest("'", "");
    }

    public boolean isLoginOrPasswordAlreadyExist(String login, String password) {
        try {
            resSet = statement.executeQuery(String.format("SELECT * FROM %s WHERE %s='%s' OR %s='%s'", TABLE_NAME, LOGIN, login, PASSWORD, password));
            return resSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean isLoginAndPasswordAlreadyExist(String login, String password) {
        try {
            resSet = statement.executeQuery(String.format("SELECT * FROM %s WHERE %s='%s' AND %s='%s'", TABLE_NAME, LOGIN, login, PASSWORD, password));
            return resSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void createPlayer(String login, String password, Warrior warrior) {
        insertRequest(LOGIN, login);
        addArg(PASSWORD, password);
        addArg(EXP, warrior.getExperience());
        addArg(HP, warrior.getHelmet());
        addArg(ATTACK, warrior.getAttack());
        addArg(DEFENSE, warrior.getDefense());
        addArg(HELMET, warrior.getHelmet());
        addArg(LEVEL, warrior.getLevel());
        addArg(TYPE, warrior.getClazz().name());
        try {
            statement.execute(request.toString());
            readDB();
        } catch (Exception e) {
            System.out.println("Не записалось в бд");
        }
    }

    public void updatePlayer(Warrior warrior) {
        request = new StringBuilder("UPDATE ").append(TABLE_NAME).append(" SET ")
                .append("'").append(EXP).append("'=")
                .append(warrior.getExperience())
                .append(",'").append(HP).append("'=")
                .append(warrior.getHp())
                .append(",'").append(HELMET).append("'=")
                .append(warrior.getHelmet())
                .append(",'").append(ATTACK).append("'=")
                .append(warrior.getAttack())
                .append(",'").append(DEFENSE).append("'=")
                .append(warrior.getDefense())
                .append(",'").append(LEVEL).append("'=")
                .append(warrior.getLevel())
                .append(" WHERE ").append(LOGIN).append("='")
                .append(warrior.getName())
                .append("';");
        try {
            statement.execute(request.toString());
            readDB();
        } catch (Exception e) {
            System.out.println("Не обновилась запись в бд");
        }
    }

    public Player readPlayer(String login, String password) {

        Player player = new Player(login);
        try {
            resSet = statement.executeQuery(String.format("SELECT * FROM %s WHERE %s='%s' AND %s='%s'", TABLE_NAME, LOGIN, login, PASSWORD, password));
            player = new Player(login, Clazz.valueOf(resSet.getString(TYPE)));
            player.setLevel(resSet.getInt(LEVEL));
            player.setExperience(resSet.getInt(EXP));
            player.setAttack(resSet.getInt(ATTACK));
            player.setHelmet(resSet.getInt(HELMET));
            player.setDefense(resSet.getInt(DEFENSE));
            player.setHp(resSet.getInt(HP));
        } catch (Exception e) {
            System.out.println("Нет такого в бд");
        }
        return player;
    }

}
