package view;

import controllers.Actions;
import controllers.AllController;
import model.DeadException;
import model.ModelView;
import model.Place;
import model.Warrior;

import java.util.*;

import static model.Warrior.*;

public class TerminalView implements MyView{
    final static private String TREE = "\033[0;32mT\033[00m";
    final static private String ANIMAL = "\033[0;31mA\033[00m";
    final static private String STONE = "\033[7;37mS\033[00m";
    final static private String PlAYER = "\033[0;36mX\033[00m";
    final static private String BOUNDARY = "\033[6;30mX\033[00m";

    private Place[][] env;
    private int width;
    private int height;

    private ModelView model;
    private AllController controllers;
    private List<String> params;
    private Warrior player;
    private String[] logs;

    private static List<String> LABELS = Arrays.asList(NAME, HP, LEVEL, EXP, ATTACK, DEFENSE, HELMET);

    public TerminalView(ModelView model, AllController controllers) {
        this.model = model;
        this.controllers = controllers;

        width = 60;
        height = 20;
        env = new Place[height][width];
        params = new ArrayList<>();

        runScanner();
    }

    private void runScanner() {
        Thread myThready = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);

            while (scanner.hasNext()) {
                String text = scanner.nextLine();

                try {
                    controllers.executeCommand(Actions.getAction(text));
                } catch (DeadException e) {
                        deadMessage();
                        controllers.closeViews();
                }
            }
        });
        myThready.setDaemon(true);
        myThready.start();
    }

    @Override
    public void refresh() {
        player = model.getPlayer();
        logs = player.getLog().replaceAll("<html>", "").replaceAll("</html>", "") .split("<br>");
        model.fillEnvironment(env);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                switch (env[i][j].getObject().getTypes()) {
                    case STONE: System.out.print(STONE); break;
                    case ANIMAL: System.out.print(ANIMAL); break;
                    case TREE: System.out.print(TREE); break;
                    case PlAYER: System.out.print(PlAYER); break;
                    case BOUNDARY: System.out.print(BOUNDARY); break;
                    default:
                        System.out.print(' ');
                }
            }
            printParam(i);
            System.out.println("");
        }
        System.out.printf("message = %s\n", model.getMessages());
    }

    private void printParam(int i) {
        if (i < LABELS.size()) {
            String label = LABELS.get(i);
            System.out.printf(" %s ", label);
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
        } else if (i < LABELS.size() + logs.length) {
            i -= LABELS.size();
            System.out.print(" ");
            System.out.print(logs[i]);

            if (i + LABELS.size() == height - 1) {
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
    public void close() {
        ;
    }
}
