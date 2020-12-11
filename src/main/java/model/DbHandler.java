package model;


import org.sqlite.JDBC;

import java.sql.*;

public class DbHandler {

    //https://habr.com/en/sandbox/88039/
    //https://alekseygulynin.ru/rabota-s-sqlite-v-java/


    private static Connection connection;
    private static Statement statement;
    private static ResultSet resSet;
    private StringBuilder request;

    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public synchronized void createConnection() throws SQLException
    {
        if (connection == null) {
            DriverManager.registerDriver(new JDBC());
            connection = DriverManager.getConnection("jdbc:sqlite:" + DbHandler.class.getResource("/database.db"));
            statement = connection.createStatement();
            System.out.println("База Подключена!");
        }
    }

    public void CloseDB() throws ClassNotFoundException, SQLException
    {
        if (connection != null)
            connection.close();
        if (statement != null)
            statement.close();
        if (resSet != null)
            resSet.close();

        System.out.println("Соединения закрыты");
    }

    public void dropTable(String name) throws SQLException
    {
        statement.execute("DROP TABLE '" + name + "';");
    }

    public void createDB() throws SQLException
    {
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
    public void initDB() throws SQLException
    {
        Warrior warrior = new Warrior("Name1", Types.PlAYER);
        addNewPlayer(warrior.getName(), "_" + warrior.getName() + "_", warrior);

        warrior = new Warrior("Name2", Types.ANIMAL);
        addNewPlayer(warrior.getName(), "_" + warrior.getName() + "_", warrior);

        warrior = new Warrior("Name3", Types.ANIMAL);
        addNewPlayer(warrior.getName(), "_" + warrior.getName() + "_", warrior);

        System.out.println("Таблица заполнена");
    }

    // -------- Вывод таблицы--------
    public void readDB() throws SQLException
    {
        resSet = statement.executeQuery("SELECT * FROM players");
        while(resSet.next())
        {
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

//    private void insertRequest(String field, int value) {
//        request = new StringBuilder("INSERT INTO 'players' ( '" + field + "' ) VALUES ( " + value + " );");
//    }

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

    public DbHandler() throws SQLException {

        createConnection();
        dropTable("players");
        createDB();
        initDB();
        readDB();


        // Регистрируем драйвер, с которым будем работать
        // в нашем случае Sqlite
//        DriverManager.registerDriver(new JDBC());
        // Выполняем подключение к базе данных
//        this.connection = DriverManager.getConnection(CON_STR);
    }

    public boolean isLoginAndPasswordAlreadyExist(String login, String password) {
        return false;
    }

    public void addNewPlayer(String login, String password, Warrior warrior) {

        try {
            insertRequest("login", login);
            addArg("password", password);
            addArg("exp", warrior.getExperience());
            addArg("hp", warrior.getHelmet());
            addArg("maxHp", warrior.getAttack());
            addArg("level", warrior.getLevel());
            statement.execute(request.toString());
            readDB();
        } catch (Exception e) {
            System.out.println("Не записалось в бд");
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
