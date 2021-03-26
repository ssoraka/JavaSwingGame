package model.war;


import model.Dice;
import model.Types;

public class Warrior extends PlaceHolder {

    public static final String NAME = "name";
    public static final String HP = "hp";
    public static final String ATTACK = "attack";
    public static final String DEFENSE = "defense";
    public static final String HELMET = "helmet";
    public static final String LEVEL = "level";
    public static final String EXP = "exp";
    public static final String TYPE = "type";

    private String name;

    private int helmet;
    private int attack;
    private int defence;

    protected int power;

    private int level;
    private int experience;
    public int expNextLevel;

    private int hp;

    private Clazz clazz;

    public Warrior(String name, Clazz clazz) {
        super(Types.CREATURE);
        this.name = name;
        this.clazz = clazz;
        init(1);
    }

    public Warrior(String name, Clazz clazz, int x, int y, int level) {
        super(Types.CREATURE, x, y);
        this.name = name;
        this.clazz = clazz;
        level = Math.max(level, 1);
        init(level);
    }

    private void init(int level){
        setLevel(level);

        switch (clazz){
            case PlAYER :
                power = 4;
                break;
            default:
                power = 1;
                experience = 105 + level * 5;
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

    public Clazz getClazz() {
        return clazz;
    }

    public boolean isAlive() {
        return (hp > 0);
    }

    public int attack() {
        return (Dice.d6() + attack + power);
    }

    protected void takeDamageFrom(Warrior enemy, int damage) {
        damage -= defence;
        if (damage > 0) {
            hp -= damage;
        }
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

    protected void attack(Warrior enemy) {
        int damage = attack();
        enemy.takeDamageFrom(this, damage);
    }

    public Warrior fight(Warrior enemy) {
        while (isAlive() && enemy.isAlive()) {
            attack(enemy);
            if (enemy.isAlive())
                enemy.attack(this);
        }

        Warrior winner;
        if (isAlive()) {
            addExperience(enemy.getExperience());
            winner = this;
        } else {
            enemy.addExperience(getExperience());
            winner = enemy;
        }
        return winner;
    }

}
