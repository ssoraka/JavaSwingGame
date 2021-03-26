package app;

import controllers.*;
import model.DAO;
import model.TestModel;
import view.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Activator implements Runnable {

    private static TestModel model;
    private static AllController controller;
    private static Activator activator;

    private List<MyView> views = new ArrayList<>();

    private static final String TERMINAL_MODE = "terminal";
    private static final String SWING_MODE = "swing";


//        System.out.print("\033[H\033[2J");
//        System.out.flush();
//        System.out.print("dsadasdadas");
//        System.out.print("\033[H\033[2J");
//        System.out.flush();


    public static void main(String[] args) throws SQLException {
        startActivator();
        String mode = getMode();

        DAO db = new DAO();
        model = new TestModel();
        model.setDb(db);

        controller = new AllController(model);

        if (TERMINAL_MODE.equals(mode)) {
            TerminalView view = new TerminalView(model, controller);
            activator.registerView(view);
        } else if (SWING_MODE.equals(mode)) {
            SwingView view = new SwingView(model, controller);
            activator.registerView(view);
        }
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
                case "2" : return TERMINAL_MODE;
                case SWING_MODE :
                case "1" : return SWING_MODE;
                default:
                    System.out.println("Choose game mode:");
                    System.out.println("1 - \"swing\"");
                    System.out.println("2 - \"terminal\"");
                    break;
            }
        }
        return SWING_MODE;
    }

    public static void startActivator() {
        activator = new Activator();
        Thread myThready = new Thread(activator);
        myThready.setDaemon(true);
        myThready.start();
    }


    public void registerView(MyView view) {
        views.add(view);
    }

//    public void unregisterViews() {
//        for (MyView view : views) {
//            view.close();
//        }
//    }

    private void refreshViews() {
        for (MyView view : views) {
            view.refresh();
        }
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (views.size() == 0)
                continue;
            if (model.wasChanged())
                refreshViews();
        }
    }
}
