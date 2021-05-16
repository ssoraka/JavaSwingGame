package model.war;

import model.Dice;

public enum Weapon {
    ARM("arm", 2, 1),
    DAGGER("dagger", 4, 1),
    SWORD("sword", 6, 1),
    AXE("axe", 6, 1),
    SPEAR("spear", 8, 1),
    LONGSWORD("longsword", 8, 1),
    DUALS("duals", 6, 2);

    private String name;
    private int maxAttack;
    private int attackCount;

    Weapon(String name, int maxAttack, int attackCount) {
        this.name = name;
        this.maxAttack = maxAttack;
        this.attackCount = attackCount;
    }

    public static Weapon randomWeapon() {
        return Weapon.values()[Dice.rand(0, Weapon.values().length - 1)];
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

    public boolean isBetterThen(Weapon other) {
        return maxAttack * attackCount >= other.maxAttack * other.attackCount;
    }
}
