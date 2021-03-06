package model;

import view.MyView;

import java.awt.*;

public interface ModelController {
    void tryMovePlayer(Point shift);
    void printMessage(String message);
    void createNewPersonAndStartGame(String login, String password);
    void findPersonAndStartGame(String login, String password);
    void exit();
    void closeViews();
}
