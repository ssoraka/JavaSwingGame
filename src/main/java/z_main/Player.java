package z_main;

import java.util.Random;

public class Player implements Warrior{
    final static int DEFAULT_HP = 10;

    private String name;

    private int level;
    private int experience;
    private int expNextLevel;

    private int hp;
    private int maxHp;
    private Random random;

    //stats
    int helmet;
    int attack;
    int defence;

    // new player
    public Player(String name) {
        this.name = name;
        hp = DEFAULT_HP;
        random = new Random();
        level = 1;
        expNextLevel = experienceForNextLevel(1);
    }

    private void upgrade() {
        helmet++;
        defence++;
        attack++;
        maxHp = helmet * 10;
        hp = maxHp;
        level++;
        expNextLevel = experienceForNextLevel(level);
    }

    private int experienceForNextLevel(int level) {
        return (level * 1000 + (level - 1) ^ 2 * 450);
    }

    @Override
    public boolean isAlive() {
        return (hp > 0);
    }

    @Override
    public int attack() {
        return (random.nextInt(attack * 5) + attack * 5);
    }

    @Override
    public void takeDamage(int damage) {
        damage -= random.nextInt(defence * 5) + defence * 5;
        if (damage > 0)
            hp -= damage;
    }

    @Override
    public int getExperience() {
        return 0;
    }

    @Override
    public void addExperience(int exp) {
        experience += exp;
        if (experience >= expNextLevel)
            upgrade();
    }
}
