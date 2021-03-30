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

    protected int startHelmet;
    protected int startAttack;
    protected int startDefense;

    protected int helmet;
    protected int attack;
    protected int defense;

    private Armor armor;
    private Weapon weapon;
    private Helmet helm;

    private int level;
    private int experience;
    public int expNextLevel;

    private int hp;

    private Clazz clazz;

    public Warrior(String name, Clazz clazz) {
        super(Types.CREATURE);
        this.name = name;
        this.clazz = clazz;

        armor = new Armor(5);
        weapon = new Weapon(5);
        setHelm(new Helmet(5));

        init(1);
    }

    public Warrior(String name, Clazz clazz, int x, int y, int level) {
        super(Types.CREATURE, x, y);
        this.name = name;
        this.clazz = clazz;

        armor = new Armor();
        weapon = new Weapon();
        setHelm(new Helmet());

        level = Math.max(level, 1);
        init(level);
    }

    private void init(int level){
        startHelmet = Dice.d6();
        startAttack = 1;
        startDefense = 1;

        switch (clazz){
            case PlAYER :
                startHelmet++;
                startAttack++;
                startDefense++;
                break;
            default:
                experience = 105 + level * 5;
        }
        setLevel(level);
        setMaxHp();
    }

    public void setHelm(Helmet helmet) {
        helm = helmet;
    }

    public void setMaxHp() {
        hp = helmet + helm.getHp();
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

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setLevel(int level) {
        this.level = level;
        experienceForNextLevel();

        helmet = startHelmet + level / 5 + 1;
        attack = startAttack + level / 5 + 1;
        defense = startDefense + level / 5 + 1;
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

    public int getDefense() {
        return defense;
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
        return (weapon.getDamage());
    }

    public int defense() {
        return (armor.getDefense());
    }

    protected void takeDamageFrom(Warrior enemy, int damage) {
//        if (Dice.d20() > defence && damage) {
//        damage -= defence;
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
            setMaxHp();
        }
    }

    private void experienceForNextLevel() {
        expNextLevel = ((level + 1) * 1000 + ((level) ^ 2) * 450);
    }

    protected boolean isDodge(Warrior enemy) {
        return Dice.d20() + enemy.attack > defense() + defense;
    }

    protected void attack(Warrior enemy) {
        if (enemy.isDodge(this)) {
            return;
        }
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
