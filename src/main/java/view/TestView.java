package view;

import controllers.ActionBuilder;
import controllers.Controller;
import model.ModelView;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TestView implements MyView, Runnable{
    private ModelView model;
    private Controller controllers;

    private MyFrame frame;
    private MyPanel panel;
    private boolean needRepaint;

    public TestView(ModelView model, Controller controllers) {
        this.model = model;
        this.controllers = controllers;

        model.registerView(this);

        panel = new MyPanel();
        panel.setPoint(model.getPlayerPos());

        frame = new MyFrame("Points",350, 250);
        frame.add(panel);
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println(e.getKeyCode());

                if (e.getKeyChar() == 'w') {
                    controllers.execute(new ActionBuilder().setAction(ActionBuilder.Action.MOVE_UP).build());
                } else if (e.getKeyChar() == 's') {
                    controllers.execute(new ActionBuilder().setAction(ActionBuilder.Action.MOVE_DOWN).build());
                } else if (e.getKeyChar() == 'a') {
                    controllers.execute(new ActionBuilder().setAction(ActionBuilder.Action.MOVE_LEFT).build());
                } else if (e.getKeyChar() == 'd') {
                    controllers.execute(new ActionBuilder().setAction(ActionBuilder.Action.MOVE_RIGHT).build());
                } else if (e.getKeyCode() == 27) {
                    System.exit(0);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        needRepaint = true;
    }

    @Override
    public void refreshView() {
//        needRepaint = true;

        frame.repaint();
    }

    @Override
    public void run() {
        if (needRepaint) {
            frame.repaint();
            needRepaint = false;
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
