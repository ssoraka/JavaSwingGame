package model;

import java.util.Random;

public class Dice {
    private static Random random = new Random();

    public static int d20() {
        return random.nextInt(20);
    }

    public static int d8() {
        return random.nextInt(8);
    }

    public static int d6() {
        return random.nextInt(6);
    }

    public static int d2() {
        return random.nextInt(2);
    }

//    public static boolean d2() {
//        return random.nextInt(2) == 1;
//    }
//
//    public static int d20(int bonus) {
//        return random.nextInt(20) + bonus;
//    }
//
//    public static int d8(int bonus) {
//        return random.nextInt(8) + bonus;
//    }
//
//    public static int d6(int bonus) {
//        return random.nextInt(6) + bonus;
//    }
}
