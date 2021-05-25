package app;

import controllers.*;
import model.DAO;
import model.MyModel;
import view.*;

import java.sql.SQLException;
import java.util.Scanner;

public class Activator {
    private static MyModel model;
    private static AllController controller;
    private static View view;

    private static final String TERMINAL_MODE = "console";
    private static final String SWING_MODE = "gui";

    private static final String USAGE =
            "java -jar swingy.jar console\n" +
            "java -jar swingy.jar gui";

    public static void main(String[] args) throws SQLException {
        String mode = SWING_MODE;
        if (args.length == 0 || (args.length == 1 && args[0].equals("-help"))) {
            System.out.println(USAGE);
            return;
        } else if (args.length == 1 && args[0].equals(TERMINAL_MODE)) {
            mode = TERMINAL_MODE;
        } else if (args.length == 1 && args[0].equals(SWING_MODE)) {
            mode = SWING_MODE;
        } else {
            System.out.println("Not valid game mode.");
            mode = getMode();
        }

        DAO db = new DAO();
        model = new MyModel();
        model.setDb(db);

        controller = new AllController(model);

        if (SWING_MODE.equals(mode)) {
            view = new View(ViewType.SWING, model, controller);
        } else if (TERMINAL_MODE.equals(mode)) {
            view = new View(ViewType.TERMINAL, model, controller);
        }

        controller.setView(view);
        controller.startMenu();
    }

    private static String getMode() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose game mode:");
        System.out.println("1) " + SWING_MODE);
        System.out.println("2) " + TERMINAL_MODE);

        while (scanner.hasNext()) {
            String text = scanner.nextLine();
            switch (text) {
                case TERMINAL_MODE :
                case "c" :
                case "t" :
                case "2" : return TERMINAL_MODE;
                case SWING_MODE :
                case "g" :
                case "s" :
                case "1" : return SWING_MODE;
                default :
                    System.out.println("Choose game mode:");
                    System.out.println("1 - \"swing\"");
                    System.out.println("2 - \"terminal\"");
                    break;
            }
        }
        return SWING_MODE;
    }
}
