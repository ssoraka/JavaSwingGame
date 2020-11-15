package model;

import view.MyView;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TestModel implements ModelController, ModelView{
    private List<MyView> views;
    private Point playerPos;
    private List<String> messages;

    public TestModel() {
        views = new ArrayList<>();
        messages = new ArrayList<>();
        playerPos = new Point(10,10);
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
        refreshAllView();
    }

    @Override
    public void printMessage(String message) {
        messages.add(message);
        refreshAllView();
    }


    //связанное с видами

    public void refreshAllView() {
        for (MyView view : views) {
            view.refreshView();
        }
    }

    //нужно указать виду, что он должен обновиться
    @Override
    public void registerView(MyView view) {
        views.add(view);
    }

    //надо отправлять какой-то интерфейс, чтоб вид мог себя обновить, пользуясь моделью
    @Override
    public String toString() {
        return "TestModel{" +
                "views=" + views +
                ", playerPos=" + playerPos +
                ", messages=" + messages +
                '}';
    }
}
