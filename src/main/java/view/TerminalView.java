package view;

import controllers.Controller;
import model.ModelView;

import java.awt.*;
import java.io.Console;
import java.io.IOException;

public class TerminalView implements MyView, Runnable{
    Console console;
    private int width;
    private int height;

    private ModelView model;
    private Controller controllers;
    private boolean needRepaint;

    public TerminalView(ModelView model) {
        this.model = model;
        model.registerView(this);

        width = 50;
        height = 15;
        needRepaint = true;
    }


    public void repaint() {
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

    @Override
    public void run() {
        while(true) {
            if (needRepaint) {
                repaint();
                needRepaint = false;
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void refreshView() {
        needRepaint = true;
    }
}
