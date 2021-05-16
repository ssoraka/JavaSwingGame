package model.items;

import model.Dice;

public enum Helmet {
    EMPTY("empty", 0),
    DIADEM("diadem", 4),
    LIGHT_HELMET("light helmet", 6),
    HEAVY_HELMET("heavy helmet", 8),
    CROWN("crown", 10);

    private String name;
    private int hp;

    Helmet(String name, int hp) {
        this.name = name;
        this.hp = hp;
    }

    public static Helmet randomHelmet() {
        return Helmet.values()[Dice.rand(0, Helmet.values().length - 1)];
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public boolean isBetterThen(Helmet other) {
        return this.hp > other.hp;
    }
}
