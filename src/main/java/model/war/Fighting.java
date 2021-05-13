package model.war;

public class Fighting {

    private static Warrior player;
    private static StringBuilder logger = new StringBuilder();
    private static boolean loggerOn = false;

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

    public static Fighter fight(Fighter hero, Fighter enemy) {
        loggerOn = hero == player || enemy == player;

        while (hero.isAlive() && enemy.isAlive()) {
            attack(hero, enemy);
            if (enemy.isAlive())
                attack(enemy, hero);
        }

        Fighter winner;
        if (hero.isAlive()) {
            hero.addExperience(enemy.getExperience());
            winner = hero;
            log(hero.getName(), " kill ", enemy.getName(), "!!!\n");
        } else {
            enemy.addExperience(hero.getExperience());
            winner = enemy;
            log(enemy.getName(), " kill ", hero.getName(), "!!!\n");
        }

        loggerOn = false;
        return winner;
    }

    public static void log(String... text) {
        if (!loggerOn) {
            return;
        }
        for (String s : text) {
            logger.append(s);
        }
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
