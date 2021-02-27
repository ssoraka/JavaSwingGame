package model;


import org.sqlite.JDBC;

import java.sql.*;

public class DAO {

    //https://habr.com/en/sandbox/88039/
    //https://alekseygulynin.ru/rabota-s-sqlite-v-java/


    private static Connection connection;
    private static Statement statement;
    private static ResultSet resSet;
    private StringBuilder request;

    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public synchronized void createConnection() throws SQLException {
        if (connection == null) {
            DriverManager.registerDriver(new JDBC());
            connection = DriverManager.getConnection("jdbc:sqlite:" + DAO.class.getResource("/database.db"));
            statement = connection.createStatement();
            System.out.println("База Подключена!");
        }
    }

    public DAO() throws SQLException {
        createConnection();
        dropTable("players");
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
        statement.execute("DROP TABLE '" + name + "';");
    }

    public void createDB() throws SQLException {
        statement.execute("CREATE TABLE if not exists 'players' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "'login' text, " +
                "'password' text, " +
                "'exp' INT, " +
                "'hp' INT, " +
                "'maxHp' INT, " +
                "'level' INT);"
        );
        System.out.println("Таблица создана или уже существует.");
    }

    // --------Заполнение таблицы--------
    public void initDB() {
        Warrior warrior = new Warrior("Name1", Types.PlAYER);
        warrior.setExperience(10000);
        warrior.setLevel(10);
        createPlayer(warrior.getName(), "_" + warrior.getName() + "_", warrior);
        warrior.setExperience(10001);
        updatePlayer(warrior);

        warrior = new Warrior("Name2", Types.ANIMAL);
        createPlayer(warrior.getName(), "_" + warrior.getName() + "_", warrior);

        warrior = new Warrior("Name3", Types.ANIMAL);
        createPlayer(warrior.getName(), "_" + warrior.getName() + "_", warrior);

        System.out.println("Таблица заполнена");
    }

    // -------- Вывод таблицы--------
    public void readDB() throws SQLException {
        resSet = statement.executeQuery("SELECT * FROM players");
        while (resSet.next()) {
            int id = resSet.getInt("id");
            String login = resSet.getString("login");
            String password = resSet.getString("password");
            int exp = resSet.getInt("exp");
            int hp = resSet.getInt("hp");
            int maxHp = resSet.getInt("maxHp");
            int level = resSet.getInt("level");
            System.out.printf("ID = %d, login = %s, pas = %s, exp = %d, hp = %d, maxHp = %d, level = %d",
                    id, login, password, exp, hp, maxHp, level);
            System.out.println();
        }

        System.out.println("Таблица выведена");
    }

    private void insertRequest(String field, String value) {
        request = new StringBuilder("INSERT INTO 'players' ( '")
                .append(field)
                .append("' ) VALUES ( '")
                .append(value)
                .append("' );");
    }

    private void updateRequest(String field, String value) {
        request = new StringBuilder("UPDATE 'players' ( '")
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
            resSet = statement.executeQuery("SELECT * FROM players WHERE login='" + login + "' OR password='" + password + "'");
            if (resSet.next())
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isLoginAndPasswordAlreadyExist(String login, String password) {
        try {
            resSet = statement.executeQuery("SELECT * FROM players WHERE login='" + login + "' AND password='" + password + "'");
            if (resSet.next())
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public void createPlayer(String login, String password, Warrior warrior) {


        insertRequest("login", login);
        addArg("password", password);
        addArg("exp", warrior.getExperience());
        addArg("hp", warrior.getHelmet());
        addArg("maxHp", warrior.getAttack());
        addArg("level", warrior.getLevel());
        try {
            statement.execute(request.toString());
            readDB();
        } catch (Exception e) {
            System.out.println("Не записалось в бд");
        }
    }

    public void updatePlayer(Warrior warrior) {
        request = new StringBuilder("UPDATE players SET")
                .append("'exp'=")
                .append(warrior.getExperience())
                .append(",'hp'=")
                .append(warrior.getHelmet())
                .append(",'maxHp'=")
                .append(warrior.getAttack())
                .append(",'level'=")
                .append(warrior.getLevel())
                .append(" WHERE login='")
                .append(warrior.getName())
                .append("';");
        try {
            statement.execute(request.toString());
            readDB();
        } catch (Exception e) {
            System.out.println("Не обновилась запись в бд");
        }
    }

    public Warrior readPlayer(String login, String password) {

        Warrior warrior = new Warrior(login, Types.PlAYER);
        try {
            resSet = statement.executeQuery("SELECT * FROM players WHERE login='" + login + "' AND password='" + password + "'");
            warrior.setExperience(resSet.getInt("exp"));
            warrior.setHelmet(resSet.getInt("hp"));
            warrior.setLevel(resSet.getInt("level"));
            warrior.setAttack(resSet.getInt("maxHp"));
        } catch (Exception e) {
            System.out.println("Нет такого в бд");
        }
        return warrior;
    }

}
