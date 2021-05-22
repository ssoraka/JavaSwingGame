package model.war;

import model.Dice;
import model.items.Armor;
import model.items.Helmet;
import model.items.Weapon;

public abstract class WarriorFabric {

    public static Warrior randomWarrior(int level) {
        Clazz clazz = Clazz.randomClass();
        Warrior warrior = new Warrior(clazz.name(), clazz);
        switch (Dice.rand(0, 4)) {
            case 1 : warrior.setArmor(Armor.randomArmor()); break;
            case 2 : warrior.setHelm(Helmet.randomHelmet()); break;
            case 3 : warrior.setWeapon(Weapon.randomWeapon()); break;
        }
        warrior.setLevel(level);
        warrior.setExperience(105 + level * 5);
        return warrior;
    }

    public static Warrior createPlayer(String name, Clazz clazz) {
        Warrior player = new Warrior(name, clazz);
        player.giveHeroStats();

        player.setArmor(Armor.HEAVY_ARMOR);
        player.setWeapon(Weapon.SPEAR);
        player.setHelm(Helmet.HEAVY_HELMET);

        player.setLevel(1);
        return player;
    }
}
