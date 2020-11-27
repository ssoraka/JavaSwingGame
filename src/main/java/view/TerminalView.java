package view;

import controllers.Controller;
import model.ModelView;

import java.awt.*;
import java.io.Console;
import java.io.IOException;

public class TerminalView implements MyView{
    Console console;
    private int width;
    private int height;

    private ModelView model;
    private Controller controllers;

    public TerminalView(ModelView model) {
        this.model = model;

        width = 50;
        height = 15;
    }

    @Override
    public void refresh() {
        Point pos = model.getPlayerPos();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i == pos.y && j == pos.x)
                    System.out.print('X');
                else
                    System.out.print(' ');
            }
            System.out.println("");
        }

        System.out.printf("message = %s\n", model.getMessages());
    }

}
