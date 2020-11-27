package model;

import view.MyView;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TestModel implements ModelController, ModelView{
    private Point playerPos;
    private List<String> messages;
    private boolean hasChange;


    public TestModel() {
        messages = new ArrayList<>();
        playerPos = new Point(5,5);
        hasChange = true;
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
    public Point getPlayerPos() {
        return playerPos;
    }

    @Override
    public List<String> getMessages() {
        return messages;
    }



    // реакции на контроллеры

    @Override
    public void tryMovePlayer(Point shift) {
        playerPos.translate(shift.x, shift.y);
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
                ", playerPos=" + playerPos +
                ", messages=" + messages +
                '}';
    }
}
