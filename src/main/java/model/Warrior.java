package model;


import java.awt.*;
import java.util.Random;

public class Warrior extends PlaceHolder{

    public static String NAME = "name";
    public static String HP = "hp";
    public static String ATTACK = "attack";
    public static String DEFENSE = "defense";
    public static String HELMET = "helmet";
    public static String LEVEL = "level";
    public static String EXP = "exp";
    public static String TYPE = "type";

    private String name;

    private int helmet;
    private int attack;
    private int defence;

    private int power;

    private int level;
    private int experience;
    public int expNextLevel;

    private int hp;
    private Random random;

    public Warrior(String name, Types type) {
        super(type);
        this.name = name;
        init(1);
    }

    public Warrior(String name, Types type, int x, int y) {
        super(type, x, y);
        this.name = name;
        init(1);
    }

    public Warrior(String name, Types type, int x, int y, int level) {
        super(type, x, y);
        this.name = name;
        init(level);
    }

    private void init(int level){
        random = new Random();
        setLevel(level);

        switch (type){
            case PlAYER :
                power = 5;
                break;
            default:
                power = 2;
                experience = expNextLevel / 10;
        }
    }

    public void setHp(int hp) {
        this.hp = hp;
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
        helmet = level  + 1;
        attack = level / 2  + 1;
        defence = level / 2  + 1;
        experienceForNextLevel();
        hp = helmet;
    }

    public int getHp() {
        return hp;
    }

    public String getName() {
        return name;
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
        return (random.nextInt(attack * power + 1));
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
        while (experience >= expNextLevel) {
            setLevel(++level);
        }
    }

    private void experienceForNextLevel() {
        expNextLevel = ((level + 1) * 1000 + (level) ^ 2 * 450);
    }
}
