package model.items;

import model.war.Fighter;

public interface Item {
    void equip(Fighter fighter);
    String getName();
}
