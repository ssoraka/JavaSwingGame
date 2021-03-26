package model;

import model.war.Player;

public interface ModelView {
    void fillEnvironment(Place[][] env);
    Player getPlayer();
}
