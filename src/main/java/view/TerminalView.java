package view;

import controllers.Actions;
import controllers.AllController;
import model.DeadException;
import model.ModelView;
import model.Place;
import model.war.Player;
import model.war.Warrior;

import java.util.*;

import static model.war.Warrior.*;

public class TerminalView implements MyView, Runnable{

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
    private List<String> params;
    private Player player;
    private String[] logs;

    private Stage stage;
    private String login;
    private String password;

    private static String[] LABELS = {NAME, HP, LEVEL, EXP, ATTACK, DEFENSE, HELMET};

    public TerminalView(ModelView model, AllController controllers) {
        this.model = model;
        this.controllers = controllers;

        width = 60;
        height = 20;
        env = new Place[height][width];
        params = new ArrayList<>();

        Thread myThready = new Thread(this);
//        myThready.setDaemon(true);
        myThready.start();

        changeStage(Stage.LOGIN);
    }

    private void changeStage(Stage newStage) {
        if (stage == newStage)
            return;
        stage = newStage;
        switch (stage) {
            case START : {
                System.out.println("Choose you destiny!!!");
                System.out.println("1) start new game");
                System.out.println("2) continue");
                System.out.println("3) quit");
                break;
            }
            case LOGIN :
                System.out.println("Enter Your Login");
                break;
            case PASSWORD :
                System.out.println("Enter Your Password");
                break;
            case PLAY :
                System.out.println("Game Start");
                refresh();
                break;
            case EXIT :
                System.out.println("Game Over");
                break;
        }
    }

    @Override
    public void refresh() {
        if (stage != Stage.PLAY)
            return;
        player = model.getPlayer();
        logs = player.getLog().split("\n");
        model.fillEnvironment(env);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                switch (env[i][j].getObject().getTypes()) {
                    case STONE: System.out.print(STONE); break;
                    case CREATURE: {
                        switch (((Warrior)env[i][j].getObject()).getClazz()) {
                            case PlAYER: System.out.print(PlAYER); break;
                            case ANIMAL: System.out.print(ANIMAL); break;
                        }
                        break;
                    }
                    case TREE: System.out.print(TREE); break;
                    case BOUNDARY: System.out.print(BOUNDARY); break;
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
                case NAME : System.out.print(player.getName()); break;
                case HP : System.out.print(player.getHp()); break;
                case LEVEL : System.out.print(player.getLevel()); break;
                case EXP : System.out.print(player.getExperience()); break;
                case DEFENSE : System.out.print(player.getDefence()); break;
                case HELMET : System.out.print(player.getHelmet()); break;
                case ATTACK : System.out.print(player.getAttack()); break;
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

        while (scanner.hasNext()) {
            String text = scanner.nextLine();

            if (stage == Stage.LOGIN) {
                login = text;
                changeStage(Stage.PASSWORD);
            } else if (stage == Stage.PASSWORD) {
                password = text;
                changeStage(Stage.START);
            } else if (stage == Stage.START) {
                if (text.equals("1")) {
                    try {
                        controllers.createNewPersonAndStartGame(login, password);
                        changeStage(Stage.PLAY);
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                        changeStage(Stage.LOGIN);
                    }
                } else if (text.equals("2")) {
                    try {
                        controllers.findPersonAndStartGame(login, password);
                        changeStage(Stage.PLAY);
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                        changeStage(Stage.LOGIN);
                    }
                } else if (text.equals("3")) {
                    controllers.exit();
                } else {
                    System.out.println("Enter the number 1-3");
                }
            } else if (stage == Stage.PLAY) {
                try {
                    controllers.executeCommand(Actions.getAction(text));
                } catch (DeadException e) {
                    deadMessage();
                    controllers.exit();
                }
            } else if (stage == Stage.EXIT) {
                controllers.exit();
            }
        }
    }


}
