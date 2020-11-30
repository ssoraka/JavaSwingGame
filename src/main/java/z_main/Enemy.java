package z_main;

import java.util.Random;

public class Enemy{
    final static int DEFAULT_HP = 1;
    //stats
    private String name;
    private int helmet;
    private int attack;
    private int defence;

    private int power;

    private int experience;

    private int hp;
    private int maxHp;
    private Random random;


    public Enemy(String name) {
        this.name = name;
        helmet = 1;
        attack = 1;
        defence = 1;
        experience = 0;
        power = 1;
        hp = 1;
        maxHp = 1;
        random = new Random();
    }

    public Enemy setHelmet(int helmet) {
        this.helmet = helmet;
        return this;
    }

    public Enemy setAttack(int attack) {
        this.attack = attack;
        return this;
    }

    public Enemy setDefence(int defence) {
        this.defence = defence;
        return this;
    }

    public void build(int power) {
        this.power = power;
        hp = helmet * power;
        maxHp = hp;
        experience = power * 500;
    }

    public boolean isAlive() {
        return (hp > 0);
    }

    public int attack() {
        return (random.nextInt(attack * power) + attack * power);
    }

    public void takeDamage(int damage) {
        damage -= random.nextInt(defence * power) + defence * power;
        if (damage > 0)
            hp -= damage;
    }

    public int getExperience() {
        return 0;
    }

    public void addExperience(int exp) {
        experience += exp;
    }

    public void fight(Enemy enemy) {
        while (enemy.isAlive() && isAlive()) {
            enemy.takeDamage(attack());
            if (enemy.isAlive())
                takeDamage(enemy.attack());
        }
        if (isAlive())
            enemy.addExperience(getExperience());
        else
            addExperience(enemy.getExperience());
    }
}
