package model;


import java.awt.*;
import java.util.Random;

public class Warrior {
    private String name;
    private Types type;
    private Point pos;

    private int helmet;
    private int attack;
    private int defence;

    private int power;

    private int level;
    private int experience;
    private int expNextLevel;

    private int hp;
    private int maxHp;
    private Random random;

    public Warrior(String name, Types type, int x, int y) {
        this.name = name;
        this.type = type;
        pos = new Point(x, y);

        helmet = 1;
        attack = 1;
        defence = 1;
        experience = 0;
        hp = 1;
        maxHp = 1;
        level = 1;
        expNextLevel = experienceForNextLevel(1);
        random = new Random();

        if (type == Types.PlAYER)
            power = 5;
        else
            power = 2;

        hp = helmet * power;
        maxHp = hp;
        experience = power * 500;
    }

    public int getX() {
        return pos.x;
    }

    public int getY() {
        return pos.y;
    }

    public Point getPos() {
        return pos;
    }

    public Types getTypes() {
        return type;
    }
/*
    public Warrior setHelmet(int helmet) {
        this.helmet = helmet;
        return this;
    }

    public Warrior setAttack(int attack) {
        this.attack = attack;
        return this;
    }

    public Warrior setDefence(int defence) {
        this.defence = defence;
        return this;
    }

    public Warrior build() {
        hp = helmet * power;
        maxHp = hp;
        experience = power * 500;
        return this;
    }*/

    public boolean isAlive() {
        return (hp > 0);
    }

    public int attack() {
        return (random.nextInt(attack * power + 1) + attack * power);
    }

    public void takeDamage(int damage) {
        damage -= defence;
        if (damage > 0)
            hp -= damage;
    }

    public int getExperience() {
        return experience;
    }

    public void addExperience(int exp) {
        experience += exp;
        if (experience >= expNextLevel) {
            level++;
            expNextLevel = experienceForNextLevel(level);
        }
    }

    private int experienceForNextLevel(int level) {
        return (level * 1000 + (level - 1) ^ 2 * 450);
    }
}
