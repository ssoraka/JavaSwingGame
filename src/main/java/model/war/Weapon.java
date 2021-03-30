package model.war;

import model.Dice;

public class Weapon {
    private static String[] NAMES = {"arm", "dagger", "sword", "axe", "spear", "longsword"};
    private static int[] ATTACKS = {2, 4, 6, 6, 8, 8};

    private String name;
    private int maxAttack;
    private int attackCount;

    public Weapon() {
        int num = Dice.rand(0, NAMES.length - 1);
        name = NAMES[num];
        maxAttack = ATTACKS[num];
        attackCount = 1;
    }

    public Weapon(int level) {
        level = Math.min(level, NAMES.length - 1);
        name = NAMES[level];
        maxAttack = ATTACKS[level];
        attackCount = 1;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        int damage = 0;
        for (int i = 0; i < attackCount; i++) {
            damage += Dice.rand(1, maxAttack);
        }
        return damage;
    }

    public static Weapon getBetter(Weapon w1, Weapon w2) {
        return w1.maxAttack > w2.maxAttack ? w1 : w2;
    }
}
