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

    private static final String TERMINAL_MODE = "terminal";
    private static final String SWING_MODE = "swing";


//        System.out.print("\033[H\033[2J");
//        System.out.flush();
//        System.out.print("dsadasdadas");
//        System.out.print("\033[H\033[2J");
//        System.out.flush();

    /*

    -добавить возможность загружать ранее созданного персонажа
    -убрать вывод базы в терминал, очищение базы в самом начале
    -надо попробовать убрать ворнинг
    May 16, 2021 7:13:20 PM org.hibernate.validator.internal.util.Version <clinit>
    INFO: HV000001: Hibernate Validator 6.0.13.Final


     */



    public static void main(String[] args) throws SQLException {
        String mode = getMode();

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
        System.out.println("1 - \"swing\"");
        System.out.println("2 - \"terminal\"");

        while (scanner.hasNext()) {
            String text = scanner.nextLine();
            switch (text) {
                case TERMINAL_MODE :
                case "t" :
                case "2" : return TERMINAL_MODE;
                case SWING_MODE :
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
