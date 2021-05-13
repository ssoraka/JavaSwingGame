package model.war;

public interface Fighter {
    String getName();
    boolean isAlive();

    int dodge();
    int attack();

    int getHp();
    int damage();
    void takeDamage(int damage);

    int getExperience();
    void addExperience(int exp);
}
