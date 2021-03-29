package view;

import controllers.Actions;
import controllers.AllController;
import model.DeadException;
import model.Dice;
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
    private AllController controller;
    private Player player;
    private String[] logs;

    private static Consumer<String> DEFAULT_ACTION = (s) -> {};
    private Consumer<String> func;
    private boolean needDestroy;


    private static String[] LABELS = {NAME, HP, LEVEL, EXP, ATTACK, DEFENSE, HELMET};

    public TerminalView(ModelView model, AllController controller) {
        this.model = model;
        this.controller = controller;
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
            switch (s) {
                case "s" :
                case "start" :
                case "1" : controller.createMenu(); break;
                case "c" :
                case "continue" :
                case "2" : controller.continueGame(); break;
                case "q" :
                case "quit" :
                case "3" : controller.exit(); break;
                default: System.out.println("Enter the number 1-3"); break;
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
                controller.setLogin(s);
                System.out.println("Enter Your Password");
                start = true;
            } else {
                controller.setPassword(s);

                try {
                    controller.createNewPersonInGame();
                    controller.startGame();
                } catch (RuntimeException ex) {
                    System.out.println(ex.getMessage());
                    controller.startMenu();
                }
            }
        };
    }

    private void identification(String login) {
        System.out.println("Enter Your Password");
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String text = scanner.nextLine().trim();
            if (text.isEmpty()) {
                continue;
            } else {
                controller.setLogin(login);
                controller.setPassword(text);
                try {
                    controller.createNewPersonInGame();
                    controller.startGame();
                } catch (RuntimeException ex) {
                    System.out.println(ex.getMessage());
                    controller.startMenu();
                }
            }
        }
    }

    @Override
    public void continueGame() {
        try {
            controller.findPersonInGame();
            controller.startGame();
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
            controller.startMenu();
        }
    }

    @Override
    public void startGame() {
        System.out.println("Game Start");
        refresh();
        func = s -> {
            try {
                Actions action = Actions.getAction(s);
                if (controller.isMeetEnemy(action) && !confirm("Do you want fight?") && Dice.d2()) {
                    controller.executeCommand(Actions.DONT_MOVE);
                } else {
                    controller.executeCommand(action);
                }
            } catch (DeadException e) {
                refresh();
                if (confirm(e.getMessage() + "\nrestart level?")) {
                    controller.continueGame();
                } else {
                    controller.startMenu();
                }
            }
        };
    }

    private boolean confirm(String message) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(message);
        System.out.println("yes/no");
        while (scanner.hasNext()) {
            String text = scanner.nextLine().trim();

            switch (text) {
                case "y":
                case "yes": return true;
                case "n":
                case "no": return false;
                default:
                    System.out.println("print \"yes\" or \"no\"");
                    break;
            }
        }
        return true;
    }

    @Override
    public void destroy() {
        needDestroy = true;
    }
}
