package model.war;

import model.Dice;

public enum Clazz {
    CAPYBARA,
    HONEY_BADGER,
    ALPACA,
    SALAMANDER;

    public static Clazz randomClass() {
        return Clazz.values()[Dice.rand(2, Clazz.values().length - 1)];
    }

}
