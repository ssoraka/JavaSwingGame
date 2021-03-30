package model.war;

import model.Dice;

public class Armor {
    private static String[] NAMES = {"empty", "robe", "light armor", "heavy armor"};
    private static int[] DEFENSE_LEVEL = {6, 10, 14, 16};

    private String name;
    private int defense;

    public Armor() {
        int num = Dice.rand(0, NAMES.length - 1);
        name = NAMES[num];
        defense = DEFENSE_LEVEL[num];
    }

    public Armor(int level) {
        level = Math.min(level, NAMES.length - 1);
        name = NAMES[level];
        defense = DEFENSE_LEVEL[level];
    }

    public String getName() {
        return name;
    }

    public int getDefense() {
        return defense;
    }

    public static Armor getBetter(Armor a1, Armor a2) {
        return a1.defense > a2.defense ? a1 : a2;
    }
}