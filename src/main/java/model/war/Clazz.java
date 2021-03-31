package model.war;

import model.Dice;

public enum Clazz {
    PlAYER,
    ANIMAL,

    SALAMANDER,
    CAPYBARA,
    HONEY_BADGER,
    ALPACA;

    public static Clazz randomClass() {
        return Clazz.values()[Dice.rand(2, Clazz.values().length - 1)];
    }

}
