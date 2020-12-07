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

    public Warrior(String name, Types type) {
        super(type);
        this.name = name;
        init();
    }

    public Warrior(String name, Types type, int x, int y) {
        super(type, x, y);
        this.name = name;
        init();
    }

    private void init(){
        helmet = 1;
        attack = 1;
        defence = 1;
        experience = 0;
        hp = 1;
        maxHp = 1;
        level = 1;
        expNextLevel = experienceForNextLevel(1);
        random = new Random();
        if (getTypes() == Types.PlAYER)
            power = 5;
        else
            power = 2;
        hp = helmet * power;
        maxHp = hp;
        experience = power * 500;
    }

    public void setHelmet(int helmet) {
        this.helmet = helmet;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getAttack() {
        return attack;
    }

    public int getHelmet() {
        return helmet;
    }

    public int getDefence() {
        return defence;
    }

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
