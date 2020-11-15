package view;

import controllers.ActionBuilder;
import controllers.Controller;
import model.ModelView;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SwingView implements MyView, Runnable{
    private ModelView model;
    private Controller controllers;

    private MyFrame frame;
    private MyPanel panel;
    private boolean needRepaint;

    public SwingView(ModelView model, Controller controllers) {
        this.model = model;
        this.controllers = controllers;

        model.registerView(this);

        panel = new MyPanel();
        panel.setPoint(model.getPlayerPos());

        frame = new MyFrame("Points",350, 250);
        frame.add(panel);
        frame.addKeyListener(new KeyListener() {

            private boolean isReader = false;
            private StringBuilder buffer = new StringBuilder();

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
//                System.out.println(e.getKeyCode());

                ActionBuilder action = new ActionBuilder();
                if (isReader) {
                    if (e.getKeyChar() == 't') {
                        action.setAction(ActionBuilder.Action.TEXT).setMessage(buffer.toString()).build();
                        isReader = false;
                    } else {
                        buffer.append(e.getKeyChar());
                        return;
                    }
                } else if (e.getKeyChar() == 't') {
                    isReader = true;
                    return;
                } else if (e.getKeyChar() == 'w') {
                    action.setAction(ActionBuilder.Action.MOVE_UP).build();
                } else if (e.getKeyChar() == 's') {
                    action.setAction(ActionBuilder.Action.MOVE_DOWN).build();
                } else if (e.getKeyChar() == 'a') {
                    action.setAction(ActionBuilder.Action.MOVE_LEFT).build();
                } else if (e.getKeyChar() == 'd') {
                    action.setAction(ActionBuilder.Action.MOVE_RIGHT).build();
                } else if (e.getKeyCode() == 27) {
                    System.exit(0);
                } else {
                    return;
                }
                controllers.execute(action);
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        needRepaint = true;
    }

    @Override
    public void refreshView() {
        needRepaint = true;
    }

    @Override
    public void run() {
        while(true) {
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
}
