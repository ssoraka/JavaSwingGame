package model;

import model.items.Item;
import model.war.Warrior;

public interface ModelView {
    void fillEnvironment(Place[][] env);
    Warrior getPlayer();
    boolean hasItems();
    Item getItem();
}
