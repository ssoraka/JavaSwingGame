package model.war;

import model.items.Item;

import java.util.ArrayList;
import java.util.List;

public class Fighting {

    private static Warrior player;
    private static StringBuilder logger = new StringBuilder();
    private static boolean loggerOn = false;
    private static List<Item> items = new ArrayList<>();

    public static void setPlayer(Warrior player) {
        Fighting.player = player;
    }

    private static void attack(Fighter hero, Fighter enemy) {
        log(hero.getName(), " attack ", enemy.getName(), ": ");

        if (hero.attack() > enemy.dodge()) {
            log("missing\n");
            return;
        }

        int damage = hero.damage();
        log("(hp=", String.valueOf(enemy.getHp()), " - ", String.valueOf(damage), " = ");

        enemy.takeDamage(damage);

        log( String.valueOf(enemy.getHp()), ")\n");
    }

    private static void reward(Fighter winner, Fighter loser) {
        log(winner.getName(), " kill ", loser.getName(), "!!!\n");
        log(winner.getName(), " get ", String.valueOf(loser.getExperience()), " experience !!!\n");
        winner.addExperience(loser.getExperience());

        List<Item> reward = new ArrayList<>();
        if (loser.getArmor().isBetterThen(winner.getArmor())) {
            reward.add(loser.getArmor());
        }
        if (loser.getHelm().isBetterThen(winner.getHelm())) {
            reward.add(loser.getHelm());
        }
        if (loser.getWeapon().isBetterThen(winner.getWeapon())) {
            reward.add(loser.getWeapon());
        }
        if (winner.equals(player)) {
            items.addAll(reward);
            return;
        }
        for (Item item : reward) {
            equip(item, winner);
        }
    }

    public static void equip(Item item, Fighter fighter) {
        log(fighter.getName(), " get ", item.getName(), "!!!\n");
        item.equip(fighter);
    }

    public static Fighter fight(Fighter hero, Fighter enemy) {
        loggerOn = hero == player || enemy == player;

        while (hero.isAlive() && enemy.isAlive()) {
            attack(hero, enemy);
            if (enemy.isAlive())
                attack(enemy, hero);
        }

        Fighter winner = hero.isAlive() ? hero : enemy;
        Fighter loser = winner == hero ? enemy : hero;
        reward(winner, loser);

        loggerOn = false;
        return winner;
    }

    private static void log(String... text) {
        if (!loggerOn) {
            return;
        }
        for (String s : text) {
            logger.append(s);
        }
    }

    public static boolean hasReward() {
        return !items.isEmpty();
    }

    public static List<Item> getItems() {
        List<Item> answer = items;
        items = new ArrayList<>();
        return answer;
    }

    public static String getTextLog() {
        String s = logger.toString();
        logger.delete(0, logger.length());
        return s;
    }

    public static String getHtmlLog() {
        int index;
        while ( (index = logger.indexOf("\n")) != -1 ) {
            logger.replace(index, index + 1, "<br>");
        }
        logger.insert(0, "<html>");
        logger.insert(logger.length(), "</html>");
        String s = logger.toString();
        logger.delete(0, logger.length());
        return s;
    }
}
