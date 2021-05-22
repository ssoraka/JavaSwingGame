package model;

import model.war.Clazz;

import java.awt.*;

public interface ModelController {
    boolean movePlayer(Point shift);
    void moveWorld();
    boolean isMeetEnemy(Point shift);
    void createNewPerson(String login, Clazz clazz);
    void findPerson(String login, String password);
    void startGame(String login, String password);
    void exit();
}
