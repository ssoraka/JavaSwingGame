package model;

import javafx.util.Pair;
//import org.sqlite.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbHandler {

    // Константа, в которой хранится адрес подключения
    private static final String CON_STR = "jdbc:sqlite:D:/myfin.db";


    // Используем шаблон одиночка, чтобы не плодить множество
    // экземпляров класса DbHandler
    private static DbHandler instance = null;

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
