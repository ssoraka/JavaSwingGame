package model.items;

import model.Dice;

public enum Armor {
    EMPTY("empty", 6),
    ROBE("robe", 10),
    LIGHT_ARMOR("light armor", 14),
    HEAVY_ARMOR("heavy armor", 16);

    private String name;
    private int defense;

    Armor(String name, int defense) {
        this.name = name;
        this.defense = defense;
    }

    public static Armor randomArmor() {
        return Armor.values()[Dice.rand(0, Armor.values().length - 1)];
    }

    public String getName() {
        return name;
    }

    public int getDefense() {
        return defense;
    }

    public boolean isBetterThen(Armor other) {
        return this.defense > other.defense;
    }
}