package model.war;

import model.Dice;
import model.Types;

public class Player extends Warrior {
    private StringBuilder logger;

    public Player(String name) {
        super(name, Clazz.PlAYER);
        power = 4;
        logger = new StringBuilder();
    }

    public Player(String name, Clazz clazz) {
        super(name, clazz);
        power = 4;
        logger = new StringBuilder();
    }

    @Override
    protected void takeDamageFrom(Warrior enemy, int damage) {
        log(enemy.getName(), " attack ", getName(), "\n");
        log("(hp=", String.valueOf(getHp()), " - ( ", String.valueOf(damage), " - ", String.valueOf(getDefence()), " ) = ");

        super.takeDamageFrom(enemy, damage);

        log( String.valueOf(getHp()), ")\n");
    }

    @Override
    protected void attack(Warrior enemy) {
        log(getName(), " attack ", enemy.getName(), "\n");

        if (Dice.d20() < enemy.getDefence()) {
            log("missing\n");
            return;
        }

        int damage = attack();
        log("(hp=", String.valueOf(enemy.getHp()), " - ", String.valueOf(enemy.getDefence()), " = ");

        enemy.takeDamageFrom(this, damage);

        log( String.valueOf(enemy.getHp()), ")\n");
    }

    @Override
    public Warrior fight(Warrior enemy) {
        clearLogger();
        Warrior winner = super.fight(enemy);

        if (winner == this) {
            log(getName(), " kill ", enemy.getName(), "!!!\n");
        } else {
            log(enemy.getName(), " kill ", getName(), "!!!\n");
        }
        return winner;
    }

    private void clearLogger() {
        logger.delete(0, logger.length());
    }

    private void log(String... text) {
        for (String s : text) {
            logger.append(s);
        }
    }

    public String getLog() {
        return logger.toString();
    }
}
