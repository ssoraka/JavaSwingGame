package view;

import controllers.Controller;
import model.ModelView;
import model.Place;

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
    private Controller controllers;

    public TerminalView(ModelView model, Controller controllers) {
        this.model = model;
        this.controllers = controllers;

        width = 60;
        height = 20;
        env = new Place[height][width];

        Thread myThready = new Thread(new TerminalScanner(controllers));
        myThready.setDaemon(true);
        myThready.start();
    }

    @Override
    public void refresh() {
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
            System.out.println("");
        }
        System.out.printf("message = %s\n", model.getMessages());
    }

}
