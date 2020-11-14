package model;

import view.MyView;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TestModel implements ModelController, ModelView{
    List<MyView> views;
    Point playerPos;
    String message;

    public TestModel() {
        this.views = new ArrayList<>();
        playerPos = new Point(10,10);
    }

    // реакции на контроллеры

    @Override
    public void tryMovePlayer(Point shift) {
        playerPos.translate(shift.x, shift.y);
        refreshAllView();
    }

    @Override
    public void printMessage(String message) {
        this.message = message;
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
    public void printModel() {
        System.out.println(playerPos.toString());
        System.out.println(message);
    }
}
