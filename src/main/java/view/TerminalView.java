package view;

import controllers.Controller;
import model.ModelView;
import model.SomeThing;

import java.awt.*;
import java.io.Console;
import java.io.IOException;

public class TerminalView implements MyView{
    private SomeThing[][] env;
    private int width;
    private int height;

    private ModelView model;
    private Controller controllers;

    public TerminalView(ModelView model) {
        this.model = model;

        width = 15;
        height = 10;
        env = new SomeThing[height][width];
    }

    @Override
    public void refresh() {
        model.fillEnvironment(env);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                switch (env[i][j].getTypes()) {
                    case STONE: System.out.print('s'); break;
                    case ANIMAL: System.out.print('A'); break;
                    case TREE: System.out.print('T'); break;
                    case PlAYER: System.out.print('0'); break;
                    case BOUNDARY: System.out.print('X'); break;
                    default:
                        System.out.print(' ');
                }
            }
            System.out.println("");
        }

/*
        Point pos = model.getPlayerPos();
        model.fillEnvironment(env);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i == pos.y && j == pos.x)
                    System.out.print('X');
                else
                    System.out.print(' ');
            }
            System.out.println("");
        }
*/
        System.out.printf("message = %s\n", model.getMessages());
    }

}
