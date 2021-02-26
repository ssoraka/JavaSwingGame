package view;

import controllers.Actions;
import controllers.AllController;
import model.ModelView;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class SwingView implements MyView{
    private ModelView model;
    private AllController controllers;

    private MyFrame frame;

    public SwingView(ModelView model, AllController controllers) {
        this.model = model;
        this.controllers = controllers;

        try {
            frame = new MyFrame("Points",800, 600);
        } catch (IOException e) {
            controllers.exit(e.getMessage());
        }
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                controllers.executeCommand(Actions.getAction(e.getKeyChar()));
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        refresh();
    }

    public void refresh() {
        model.fillEnvironment(frame.getEnv());
        frame.repaint();
    }
}
