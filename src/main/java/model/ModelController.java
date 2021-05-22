package model;

import model.war.Clazz;

import java.awt.*;

public interface ModelController {
    boolean movePlayer(Point shift);
    void moveWorld();
    boolean isMeetEnemy(Point shift);
    void createNewPersonAndStartGame(String login, String password, Clazz clazz);
    void findPersonAndStartGame(String login, String password);
    void exit();
}
