package view;

import controllers.Actions;
import controllers.AllController;
import model.DeadException;
import model.ModelView;
import model.Place;
import model.war.Player;
import model.war.Warrior;

import java.util.Scanner;
import java.util.function.Consumer;

import static model.war.Warrior.*;

public class TerminalView implements MyView, Runnable {

    private final static String TREE = "\033[0;32mT\033[00m";
    private final static String ANIMAL = "\033[0;31mA\033[00m";
    private final static String STONE = "\033[7;37mS\033[00m";
    private final static String PlAYER = "\033[0;36mX\033[00m";
    private final static String BOUNDARY = "\033[6;30mX\033[00m";

    private Place[][] env;
    private int width;
    private int height;

    private ModelView model;
    private AllController controllers;
    private Player player;
    private String[] logs;

    private static Consumer<String> DEFAULT_ACTION = (s) -> {};
    private Consumer<String> func;
    private boolean needDestroy;


    private static String[] LABELS = {NAME, HP, LEVEL, EXP, ATTACK, DEFENSE, HELMET};

    public TerminalView(ModelView model, AllController controllers) {
        this.model = model;
        this.controllers = controllers;
        func = DEFAULT_ACTION;

        width = 60;
        height = 20;
        env = new Place[height][width];

        new Thread(this).start();
//        myThready.setDaemon(true);
    }

    @Override
    public void refresh() {
        player = model.getPlayer();
        logs = player.getLog().split("\n");
        model.fillEnvironment(env);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                switch (env[i][j].getObject().getTypes()) {
                    case STONE:
                        System.out.print(STONE);
                        break;
                    case CREATURE: {
                        switch (((Warrior) env[i][j].getObject()).getClazz()) {
                            case PlAYER:
                                System.out.print(PlAYER);
                                break;
                            case ANIMAL:
                                System.out.print(ANIMAL);
                                break;
                        }
                        break;
                    }
                    case TREE:
                        System.out.print(TREE);
                        break;
                    case BOUNDARY:
                        System.out.print(BOUNDARY);
                        break;
                    default:
                        System.out.print(' ');
                }
            }
            printParamOrLogs(i);
            System.out.println("");
        }
    }

    private void printParamOrLogs(int i) {
        if (i < LABELS.length) {
            String label = LABELS[i];
            System.out.printf(" %15s  ", label);
            switch (label) {
                case NAME:
                    System.out.print(player.getName());
                    break;
                case HP:
                    System.out.print(player.getHp());
                    break;
                case LEVEL:
                    System.out.print(player.getLevel());
                    break;
                case EXP:
                    System.out.print(player.getExperience());
                    break;
                case DEFENSE:
                    System.out.print(player.getDefence());
                    break;
                case HELMET:
                    System.out.print(player.getHelmet());
                    break;
                case ATTACK:
                    System.out.print(player.getAttack());
                    break;
                default:
                    break;
            }
        } else if (i < LABELS.length + logs.length) {
            i -= LABELS.length;
            System.out.print(" ");
            System.out.print(logs[i]);

            if (i + LABELS.length == height - 1) {
                while (++i < logs.length) {
                    System.out.print(" ");
                    System.out.print(logs[i]);
                }
            }
        }
    }

    private void deadMessage() {
        System.out.println("dead!!");
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (!needDestroy && scanner.hasNext()) {
            String text = scanner.nextLine();

            if (!text.trim().isEmpty()) {
                func.accept(text);
            }
        }
    }


    @Override
    public void startMenu() {
        System.out.println("Choose you destiny!!!");
        System.out.println("1) start new game");
        System.out.println("2) continue");
        System.out.println("3) quit");
        func = s -> {
            if (s.equals("1")) {
                controllers.createMenu();
            } else if (s.equals("2")) {
                controllers.continueGame();
            } else if (s.equals("3")) {
                controllers.exit();
            } else {
                System.out.println("Enter the number 1-3");
            }
        };
    }

    boolean start;
    @Override
    public void createMenu() {
        System.out.println("Enter Your Login");
        start = false;

        func = s -> {
            if (!start) {
                controllers.setLogin(s);
                System.out.println("Enter Your Password");
                start = true;
            } else {
                controllers.setPassword(s);

                try {
                    controllers.createNewPersonInGame();
                    controllers.startGame();
                } catch (RuntimeException ex) {
                    System.out.println(ex.getMessage());
                    controllers.startMenu();
                }
            }
        };
    }

    @Override
    public void continueGame() {
        try {
            controllers.findPersonInGame();
            controllers.startGame();
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
            controllers.startMenu();
        }
    }

    @Override
    public void startGame() {
        System.out.println("Game Start");
        refresh();
        func = s -> {
            try {
                controllers.executeCommand(Actions.getAction(s));
            } catch (DeadException e) {
                deadMessage();
                controllers.startMenu();
            }
        };
    }

    @Override
    public void destroy() {
        needDestroy = true;
    }
}
