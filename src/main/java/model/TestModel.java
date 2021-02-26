package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TestModel implements ModelController, ModelView {
    private DbHandler db;
    private List<String> messages;
    private boolean hasChange;

    private Level level;
    private Warrior player;

    public TestModel() {
        messages = new ArrayList<>();
        hasChange = true;


        setPlayer(new Warrior("capybara", Types.PlAYER, 0, 0));
    }

    public void setDb(DbHandler db) {
        this.db = db;
    }

    @Override
    public boolean hasChange() {
        return hasChange;
    }

    @Override
    public void applayChanges() {
        hasChange = false;
    }

    @Override
    public List<String> getMessages() {
        return messages;
    }

    @Override
    public void fillEnvironment(Place[][] env) {
        level.fillEnvironment(env);
    }


    private void setPlayer(Warrior warrior) {
        player = warrior;
        level = new Level(player);
    }


    // реакции на контроллеры

    @Override
    public void tryMovePlayer(Point shift) {
        level.tryMoveObject(player, shift);
        level.moveAnimals();
        hasChange = true;
    }

    @Override
    public void printMessage(String message) {
        messages.add(message);
        hasChange = true;
    }

    @Override
    public void createNewPersonAndStartGame(String login, String password) {
        if (db.isLoginOrPasswordAlreadyExist(login, password))
            throw new RuntimeException("Такое имя или пароль уже существует");
        db.addNewPlayer(login, password, player);
    }

    @Override
    public void findPersonAndStartGame(String login, String password) {
        if (!db.isLoginAndPasswordAlreadyExist(login, password))
            throw new RuntimeException("Неверное имя или пароль");
        setPlayer(db.readPlayer(login, password));
    }

    @Override
    public void exit(String message) {
        if (message != null)
            System.out.println(message);
        if (db != null) {
            try {
                db.CloseDB();
                System.out.println("База отключена");
            } catch (Exception e) {
                ;
            }
        }
        System.exit(0);
    }


    //надо отправлять какой-то интерфейс, чтоб вид мог себя обновить, пользуясь моделью
    @Override
    public String toString() {
        return "TestModel{" +
                ", messages=" + messages +
                '}';
    }
}
