package model.war;

import model.items.Armor;
import model.items.Helm;
import model.items.Weapon;

public interface Fighter {
    String getName();
    boolean isAlive();

    int dodge();
    int attack();

    int getHp();
    int damage();
    void takeDamage(int damage);

    int getExperience();
    void addExperience(int exp);

    Armor getArmor();
    Weapon getWeapon();
    Helm getHelm();

    void setHelm(Helm helm);
    void setWeapon(Weapon weapon);
    void setArmor(Armor armor);
}
