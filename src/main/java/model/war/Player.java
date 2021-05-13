package model.war;

public class Player implements Fighter{
    private Warrior warrior;



    @Override
    public String getName() {
        return warrior.getName();
    }

    @Override
    public boolean isAlive() {
        return warrior.isAlive();
    }

    @Override
    public int dodge() {
        return warrior.dodge();
    }

    @Override
    public int attack() {
        return warrior.attack();
    }

    @Override
    public int getHp() {
        return warrior.getHp();
    }

    @Override
    public int damage() {
        return 0;
    }

    @Override
    public void takeDamage(int damage) {

    }

    @Override
    public int getExperience() {
        return 0;
    }

    @Override
    public void addExperience(int exp) {

    }
}
