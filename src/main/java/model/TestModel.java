package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TestModel implements ModelController, ModelView {
    final static private int WIDTH = 100;
    final static private int HEIGHT = 100;

    private List<String> messages;
    private boolean hasChange;

    private Level level;
    private Warrior player;

    public TestModel() {
        messages = new ArrayList<>();
        hasChange = true;
        level = new Level(WIDTH, HEIGHT);

        player = new Warrior("capybara", Types.PlAYER, 5, 5);
        level.setPlayer(player);
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


    //надо отправлять какой-то интерфейс, чтоб вид мог себя обновить, пользуясь моделью
    @Override
    public String toString() {
        return "TestModel{" +
                ", messages=" + messages +
                '}';
    }
}
