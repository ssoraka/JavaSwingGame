package model;

import javafx.util.Pair;
import org.sqlite.JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbHandler {

    // Константа, в которой хранится адрес подключения
    private static final String CON_STR = "jdbc:sqlite:D:/myfin.db";

    //https://habr.com/en/sandbox/88039/
    //https://alekseygulynin.ru/rabota-s-sqlite-v-java/

    // Используем шаблон одиночка, чтобы не плодить множество
    // экземпляров класса DbHandler
    private static DbHandler instance = null;

    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;

    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public static void Conn() throws ClassNotFoundException, SQLException
    {
        conn = null;
//        Class.forName("org.sqlite.JDBC");
        DriverManager.registerDriver(new JDBC());
        conn = DriverManager.getConnection("jdbc:sqlite:/Users/ssoraka/IdeaProjects/myGame/src/main/resources/database.db");

        System.out.println("База Подключена!");
    }

    public static void CloseDB() throws ClassNotFoundException, SQLException
    {
        conn.close();
//        statmt.close();
//        resSet.close();

        System.out.println("Соединения закрыты");
    }

    public static synchronized DbHandler getInstance() throws SQLException {
        if (instance == null)
            instance = new DbHandler();
        return instance;
    }

    // Объект, в котором будет храниться соединение с БД
    private Connection connection;

    List<Pair<String, String>> db;

    public DbHandler() throws SQLException {
        db = new ArrayList<>();
        db.add(new Pair<>("1", "1"));
        db.add(new Pair<>("2", "2"));
        db.add(new Pair<>("3", "3"));

        // Регистрируем драйвер, с которым будем работать
        // в нашем случае Sqlite
//        DriverManager.registerDriver(new JDBC());
        // Выполняем подключение к базе данных
//        this.connection = DriverManager.getConnection(CON_STR);
    }

    public boolean isLoginAndPasswordAlreadyExist(String login, String password) {
        for (Pair<String, String> pair :
                db) {
            if (pair.getKey().equals(login) || pair.getValue().equals(password))
                return true;
        }
        return false;
    }

    public void addNewPlayer(String login, String password) {
        db.add(new Pair<>(login, password));
    }
}
