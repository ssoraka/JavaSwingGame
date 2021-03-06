package z_main;

import controllers.*;
import model.DAO;
import model.TestModel;
import view.*;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Activator implements Runnable {

    private static TestModel model;
    private static AllController controller;
    private static SimpleGUI app; //menu
    private static Activator activator;

    private List<MyView> views = new ArrayList<>();



    /*
    надо сделать интерфейс для терминала
    вывести параметры персонажа
     */


    public static void main(String[] args) {
        startActivator();

//        System.out.print("\033[H\033[2J");
//        System.out.flush();
//
//        System.out.print("dsadasdadas");
//
//        System.out.print("\033[H\033[2J");
//        System.out.flush();

        DAO db = null;
        try {
            db = new DAO();
//            db.CloseDB();
        } catch (Exception e) {
            System.exit(0);
        }
//        System.exit(0);

        model = new TestModel();
        model.setDb(db);

        controller = new AllController(model);

//        openMenu();

        TerminalView view2 = new TerminalView(model, controller);
        activator.registerView(view2);

//        controller.findPersonAndStartGame("Capybara", "_Capybara_");

//        startGame();
    }

    public static void openMenu() {
        app = new SimpleGUI(controller);
    }

    public static void startActivator() {
        activator = new Activator();
        Thread myThready = new Thread(activator);
//        myThready.setDaemon(true);
        myThready.start();
    }

    public static void startGame() {
        app = null;

        SwingView view = new SwingView(model, controller);
        activator.registerView(view);

        TerminalView view2 = new TerminalView(model, controller);
        activator.registerView(view2);
    }

    public void registerView(MyView view) {
        views.add(view);
    }

    public void unregisterViews() {
        for (MyView view : views) {
            view.close();
        }
        views = new ArrayList<>();
        openMenu();
    }

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
            //надо это отладить, чтоб не убивало окна в начале игры
//            if (!model.isGameRun())
//                unregisterViews();
        }
    }
}