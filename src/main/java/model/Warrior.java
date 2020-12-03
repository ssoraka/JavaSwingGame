package model;


import java.awt.*;
import java.util.Random;

public class Warrior extends PlaceHolder{
    private String name;

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
        super(type, x, y);
        this.name = name;

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

    public int getLevel() {
        return level;
    }

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
