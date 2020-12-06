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
        try {
            db = new DbHandler();
        } catch (Exception e) {
            System.exit(0);
        }
        messages = new ArrayList<>();
        hasChange = true;


        player = new Warrior("capybara", Types.PlAYER, 0, 0); // надо поправить конструктор
        level = new Level(player);
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
        if (db.isLoginAndPasswordAlreadyExist(login, password))
            throw new RuntimeException("Такое имя или пароль уже существует");
        db.addNewPlayer(login, password);
    }


    //надо отправлять какой-то интерфейс, чтоб вид мог себя обновить, пользуясь моделью
    @Override
    public String toString() {
        return "TestModel{" +
                ", messages=" + messages +
                '}';
    }
}
