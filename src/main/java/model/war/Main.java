package model.war;

import model.Types;

public class Main {
    public static void main(String[] args) {
        Player player = new Player("me");
        player.setHp(20);

        Warrior warrior = new Warrior("enemy", Clazz.ANIMAL);
        warrior.setHp(20);

        warrior.fight(player);
//        player.fight(warrior);
        System.out.println(player.getLog());
    }
}
