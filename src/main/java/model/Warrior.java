package model;


import java.awt.*;
import java.util.Random;

public class Warrior extends PlaceHolder{

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

    private int power;

    private int level;
    private int experience;
    public int expNextLevel;

    private int hp;
    private Random random;

    private StringBuilder logger;
    private StringBuilder enemyLogger;

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
        level = Math.max(level, 1);
        init(level);
    }

    private void init(int level){
        random = new Random();
        setLevel(level);

        switch (type){
            case PlAYER :
                power = 4;
                logger = new StringBuilder();
                break;
            default:
                power = 1;
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
        return (random.nextInt(attack + power + 1));
    }

    public void takeDamage(int damage) {
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

    private void attack(Warrior enemy) {
        int damage = attack();

        log(name, " attack ", enemy.getName(), "<br>");
        log("(hp=", String.valueOf(enemy.getHp()), " - ( ", String.valueOf(damage), " - ", String.valueOf(enemy.getDefence()), " ) = ");

        enemy.takeDamage(damage);

        log( String.valueOf(enemy.getHp()), ")<br>");
    }

    public Warrior fight(Warrior enemy) {
        enemyLogger = enemy.logger;
        clearLogger();
        log("<html>");
        while (isAlive() && enemy.isAlive()) {
            attack(enemy);
            if (enemy.isAlive())
                enemy.attack(this);
        }

        Warrior winner;
        if (isAlive()) {
            log(name, " kill ", enemy.getName(), "!!!<br>");
            addExperience(enemy.getExperience());
            winner = this;
        } else {
            log(enemy.getName(), " kill ", name, "!!!<br>");
            enemy.addExperience(getExperience());
            winner = enemy;
        }
        log("</html>");
        enemyLogger = null;
        return winner;
    }

    private void clearLogger() {
        if (logger != null){
            logger.delete(0, logger.length());
        }
        if (enemyLogger != null){
            enemyLogger.delete(0, enemyLogger.length());
        }
    }

    private void log(String ... text) {
        if (logger != null) {
            for (String s : text) {
                logger.append(s);
            }
        }
        if (enemyLogger != null) {
            for (String s : text) {
                enemyLogger.append(s);
            }
        }
    }

    public String getLog() {
        return logger.toString();
    }

}
