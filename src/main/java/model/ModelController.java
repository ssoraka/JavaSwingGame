package model;

import java.awt.*;

public interface ModelController {
    void tryMovePlayer(Point shift);
    boolean isMeetEnemy(Point shift);
    void createNewPersonAndStartGame(String login, String password);
    void findPersonAndStartGame(String login, String password);
    void exit();
}
