package view;

import controllers.Controller;
import model.ModelView;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SwingView implements MyView{
    private ModelView model;
    private Controller controllers;

    private MyFrame frame;

    public SwingView(ModelView model, Controller controllers) {
        this.model = model;
        this.controllers = controllers;

        frame = new MyFrame("Points",350, 250);
        frame.addKeyListener(new KeyListener() {

            private CommandReader reader = new CommandReader();

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
//                System.out.println(e.getKeyCode());
                controllers.execute(reader.getAction(e));
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
