package view;

import controllers.Actions;
import controllers.AllController;
import model.*;
import model.items.Item;
import model.war.Clazz;
import model.war.Fighting;
import model.war.Warrior;

import java.util.Scanner;

import static model.war.Warrior.*;

public class TerminalView implements MyView, Runnable {

    private final static String TREE = "\033[2;0;32mT\033[00m";
    private final static String BLOOD = "\033[41;30m \033[00m";
    private final static String EMPTY = " ";
    private final static String SALAMANDER = "\033[2;45;30ms\033[00m";
    private final static String CAPYBARA = "\033[2;41;30mc\033[00m";
    private final static String ALPACA = "\033[2;42;30ma\033[00m";
    private final static String HONEY_BADGER = "\033[2;44;30mh\033[00m";
    private final static String STONE = "\033[7;37ms\033[00m";
    private final static String BOUNDARY = "\033[1;30mX\033[00m";

    private Place[][] env;
    private int width;
    private int height;

    private ModelView model;
    private AllController controller;
    private Warrior player;
    private String[] logs;
    private String emptyLine;

    private static Runnable DEFAULT_ACTION = new Runnable() {
        @Override
        public void run() {}
    };
    private Runnable runner;
    private boolean needDestroy;


    private static String[] LABELS = {NAME, HP, LEVEL, EXP, ATTACK, DEFENSE, HIT_POINTS, ARMOR, WEAPON, HELM};

    public TerminalView(ModelView model, AllController controller) {
        this.model = model;
        this.controller = controller;
        runner = DEFAULT_ACTION;

        width = 60;
        height = 20;
        env = new Place[height][width];

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < width + 1; i++) {
            stringBuilder.append(" ");
        }
        emptyLine = stringBuilder.toString();


        Thread thread = new Thread(this);
        thread.setName("Terminal");
        thread.start();
    }

    private String getText(int i, int j) {

        if (env[i][j].hasWarrior()) {
            switch (env[i][j].getWarrior().getClazz()) {
                case SALAMANDER: return SALAMANDER;
                case CAPYBARA: return CAPYBARA;
                case ALPACA: return ALPACA;
                case HONEY_BADGER: return HONEY_BADGER;
                default:
                    return EMPTY;
            }
        }

        switch (env[i][j].getType()) {
            case BOUNDARY: return BOUNDARY;
            case STONE: return STONE;
            case TREE: return TREE;
            default: {
                switch (env[i][j].getType()) {
                    case BLOOD: return BLOOD;
                    default:
                        return EMPTY;
                }
            }
        }
    }

    public void refresh() {
        System.out.println("\33c");
        player = model.getPlayer();
        logs = Fighting.getTextLog().split("\n");
        model.fillEnvironment(env);

        String text = "";
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                text = getText(i, j);
                if (i == height / 2 && j == width / 2) {
                    String c = text.charAt(10) + "";
                    text = text.replace(c, c.toUpperCase());
                }
                System.out.print(text);
            }
            printParamOrLogs(i);
            System.out.println("");
        }
        for (int i = height - LABELS.length; i < logs.length; i++) {
            System.out.print(emptyLine);
            System.out.println(logs[i]);
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
                    System.out.print('/');
                    System.out.print(player.maxHp());
                    break;
                case LEVEL:
                    System.out.print(player.getLevel());
                    break;
                case EXP:
                    System.out.print(player.getExperience());
                    System.out.print('/');
                    System.out.print(player.getExperienceForNextLevel());
                    break;
                case DEFENSE:
                    System.out.print(player.getDefense());
                    break;
                case HIT_POINTS:
                    System.out.print(player.getHitPoints());
                    break;
                case ATTACK:
                    System.out.print(player.getAttack());
                    break;
                case ARMOR:
                    System.out.print(player.getArmor());
                    break;
                case WEAPON:
                    System.out.print(player.getWeapon());
                    break;
                case HELM:
                    System.out.print(player.getHelm());
                    break;
                default:
                    break;
            }
        } else if (i < LABELS.length + logs.length) {
            i -= LABELS.length;
            System.out.print(" ");
            System.out.print(logs[i]);
        }
    }

    @Override
    public void run() {
        while (!needDestroy) {
            runner.run();

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void startMenu() {
        System.out.println("\33c");
        System.out.println("Choose you destiny!!!");
        System.out.println("1) start new game");
        System.out.println("2) continue");
        System.out.println("3) quit");

        runner = new Runnable() {
            @Override
            public void run() {
                switch (readLine()) {
                    case "s":
                    case "start":
                    case "1": controller.createMenu(); break;
                    case "c":
                    case "continue":
                    case "2": controller.continueGame(); break;
                    case "q":
                    case "quit":
                    case "3": controller.exit(); break;
                    default: System.out.println("Enter the number 1-3"); break;
                }
            }
        };
    }

    @Override
    public void createMenu() {
        runner = new Runnable() {
            @Override
            public void run() {
                System.out.println("Enter Your Login");
                controller.setLogin(readLine());
                System.out.println("Enter Your Password");
                controller.setPassword(readLine());
                controller.setClazz(readClass());
                try {
                    controller.createNewPersonInGame();
                    controller.watchHero();
                } catch (DAOException ex) {
                    System.out.println(ex.getMessage());
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {}
                    controller.startMenu();
                }
            }
        };
    }

    @Override
    public void continueGame() {
        runner = new Runnable() {
            @Override
            public void run() {
                System.out.println("Enter Your Login");
                controller.setLogin(readLine());
                System.out.println("Enter Your Password");
                controller.setPassword(readLine());
                try {
                    controller.findPersonInGame();
                    controller.watchHero();
                } catch (DAOException ex) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {}
                    controller.startMenu();
                }
            }
        };
    }

    @Override
    public void watchHero() {
        runner = new Runnable() {
            @Override
            public void run() {
                player = model.getPlayer();

                System.out.println("Your hero:");
                for (int i = 0; i < LABELS.length; i++) {
                    printParamOrLogs(i);
                    System.out.println("");
                }
                System.out.println("");

                if (confirm("Do you accept it?")) {
                    controller.startGame();
                } else {
                    controller.startMenu();
                }
            }
        };
    }

    @Override
    public void startGame() {
        runner = new Runnable() {
            @Override
            public void run() {
                refresh();
                try {
                    Actions action = Actions.getAction(readLine());
                    if (controller.isMeetEnemy(action) && !confirm("Do you want fight?") && Dice.d2()) {
                        return;
                    } else {
                        if (controller.executeCommand(action)) {
                            getReward();
                            controller.moveWorld();
                            getReward();
                        }
                    }
                } catch (DeadException e) {
                    refresh();
                    if (confirm(e.getMessage() + "\nrestart level?")) {
                        controller.startGame();
                    } else {
                        controller.startMenu();
                    }
                }
            }
        };
    }

    private void getReward() {
        while (model.hasItems()) {
            Item item = model.getItem();
            if (confirm("Do you want equip " + item.getName() + "?")) {
                item.equip(model.getPlayer());
            }
        }
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

    private String readLine() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().trim();
    }

    private Clazz readClass() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Your Class");
        System.out.println("1) Capybara");
        System.out.println("2) Honey badger");
        System.out.println("3) Alpaca");
        System.out.println("4) Salamander");
        while (scanner.hasNext()) {
            String text = scanner.nextLine().trim();

            switch (text.toLowerCase()) {
                case "1":
                case "c":
                case "capybara": return Clazz.CAPYBARA;
                case "2":
                case "h":
                case "honey":
                case "honey badger": return Clazz.HONEY_BADGER;
                case "3":
                case "a":
                case "alpaca": return Clazz.ALPACA;
                case "4":
                case "s":
                case "salamander": return Clazz.SALAMANDER;
                default:
                    System.out.println("Enter the number 1-4 or first letter"); break;
            }

        }
        return Clazz.CAPYBARA;
    }

    @Override
    public void destroy() {
        needDestroy = true;
    }
}
