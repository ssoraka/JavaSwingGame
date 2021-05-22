package model.war;


import model.Dice;
import model.items.Armor;
import model.items.Helmet;
import model.items.Weapon;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class Warrior implements Fighter{

    public static final String NAME = "name";
    public static final String HP = "hp";
    public static final String ATTACK = "attack";
    public static final String DEFENSE = "defense";
    public static final String HELMET = "helmet";
    public static final String LEVEL = "level";
    public static final String EXP = "exp";
    public static final String TYPE = "type";

    public static final String WEAPON = "weapon";
    public static final String ARMOR = "armor";
    public static final String HELM = "helm";

    private String name;

    private int startHelmet;
    private int startAttack;
    private int startDefense;

    @Min(value = 1, message = "Parameter helmet should not be less than 1")
    private int helmet;
    @Min(value = 1, message = "Parameter attack should not be less than 1")
    private int attack;
    @Min(value = 1, message = "Parameter defense should not be less than 1")
    private int defense;

    @NotNull(message = "Armor cannot be null")
    private Armor armor;
    @NotNull(message = "Weapon cannot be null")
    private Weapon weapon;
    @NotNull(message = "Helmet cannot be null")
    private Helmet helm;

    @Min(value = 1, message = "Level should not be less than 1")
    private int level;
    @Min(value = 0, message = "Level should not be less than 1")
    private int experience;
    public int expNextLevel;

    private int hp;

    @NotNull(message = "Class cannot be null")
    private Clazz clazz;

    public Warrior(String name, Clazz clazz) {
        this.name = name;
        this.clazz = clazz;

        startHelmet = 5;
        startAttack = 1;
        startDefense = 1;

        setArmor(Armor.EMPTY);
        setWeapon(Weapon.ARM);
        setHelm(Helmet.EMPTY);

        setLevel(1);
    }

    public void giveHeroStats() {
        switch (clazz){
            case ALPACA:
                startDefense = 3;
                break;
            case CAPYBARA:
                startHelmet = 6;
                startAttack = 2;
                startDefense = 2;
                break;
            case SALAMANDER:
                startAttack = 3;
                break;
            case HONEY_BADGER:
                startHelmet = 7;
                break;
        }
        heel();
    }

    public void setHelm(Helmet helmet) {
        helm = helmet;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }

    public int maxHp() {
        return helmet + helm.getHp();
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
        hp = maxHp();
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

    public Armor getArmor() {
        return armor;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public Helmet getHelm() {
        return helm;
    }

    public int getLevel() {
        return level;
    }

    public Clazz getClazz() {
        return clazz;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isAlive() {
        return (hp > 0);
    }

    @Override
    public int getHp() {
        return hp;
    }

    @Override
    public int attack() {
        return Dice.d20() + attack;
    }

    @Override
    public int damage() {
        return weapon.getDamage();
    }

    @Override
    public void takeDamage(int damage) {
        if (damage > 0) {
            hp -= damage;
        }
        if (hp < 0) {
            hp = 0;
        }
    }

    @Override
    public int dodge() {
        return armor.getDefense() + defense;
    }

    @Override
    public int getExperience() {
        return experience;
    }

    @Override
    public void addExperience(int exp) {
        experience += exp;
        while (experience >= expNextLevel) {
            setLevel(++level);
        }
    }

    public int getExperienceForNextLevel() {
        return expNextLevel;
    }

    private void experienceForNextLevel() {
        expNextLevel = level * 1000 + (int)Math.pow(level - 1, 2) * 450;
    }

    public void heel() {
        hp = maxHp();
    }
}
