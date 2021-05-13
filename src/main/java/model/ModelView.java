package model;

import model.war.Warrior;

public interface ModelView {
    void fillEnvironment(Place[][] env);
    Warrior getPlayer();
}
