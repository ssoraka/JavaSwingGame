package view;

import controllers.Actions;
import controllers.AllController;
import model.DeadException;
import model.ModelView;

import javax.swing.*;
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
            controllers.exit();
        }
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                try {
                    controllers.executeCommand(Actions.getAction(e.getKeyChar()));
                } catch (DeadException ex) {
                    JOptionPane.showMessageDialog(null,
                            ex.getMessage(),
                            "Game Over\n Player is dead!!!",
                            JOptionPane.PLAIN_MESSAGE);
                    controllers.closeViews();
                }

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

    @Override
    public void close() {
        frame.dispose();
    }
}
