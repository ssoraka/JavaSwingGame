package model.war;

import model.Dice;

public class Helmet {
    private static String[] NAMES = {"empty", "diadem", "light helmet", "heavy helmet", "crown"};
    private static int[] HEATH_POINTS = {0, 4, 6, 8, 10};

    private String name;
    private int hp;

    public Helmet() {
        int num = Dice.rand(0, NAMES.length - 1);
        name = NAMES[num];
        hp = HEATH_POINTS[num];
    }

    public Helmet(int level) {
        level = Math.min(level, NAMES.length - 1);
        name = NAMES[level];
        hp = HEATH_POINTS[level];
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public static Helmet getBetter(Helmet h1, Helmet h2) {
        return h1.hp > h2.hp ? h1 : h2;
    }
}
