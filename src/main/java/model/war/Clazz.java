package model.war;

import model.Dice;

public enum Clazz {
    CAPYBARA,
    HONEY_BADGER,
    ALPACA,
    SALAMANDER;

    public static Clazz randomClass() {
        return Clazz.values()[Dice.rand(0, Clazz.values().length - 1)];
    }

}
