package z_main;

public interface Warrior {
    public boolean isAlive();
    public int attack();
    public void takeDamage(int damage);
    public int getExperience();
    public void addExperience(int exp);

    default void fight(Warrior enemy) {
        while (enemy.isAlive() && isAlive()) {
            enemy.takeDamage(attack());
            if (enemy.isAlive())
                takeDamage(enemy.attack());
        }
        if (isAlive())
            enemy.addExperience(getExperience());
        else
            addExperience(enemy.getExperience());
    }
}
