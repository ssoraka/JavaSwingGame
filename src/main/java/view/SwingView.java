package view;

import controllers.Actions;
import controllers.AllController;
import model.DeadException;
import model.ModelView;
import model.Warrior;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class SwingView implements MyView{
    private ModelView model;

    private MyFrame frame;

    public SwingView(ModelView model, AllController controllers) {
        this.model = model;

        try {
            frame = new MyFrame("Points",800, 600);
        } catch (IOException e) {
            controllers.exit();
        }
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                try {
                    controllers.executeCommand(Actions.getAction(e.getKeyChar()));
                } catch (DeadException ex) {
                    JOptionPane.showMessageDialog(null,
                            "Player is dead!!!",
                            "Game Over",
                            JOptionPane.PLAIN_MESSAGE);
                    controllers.closeViews();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        refresh();
    }

    public void refresh() {
        model.fillEnvironment(frame.getEnv());
        Warrior person = model.getPlayer();
        frame.setPersonName(person.getName());
        frame.setDefense(person.getDefence());
        frame.setHelmet(person.getHelmet());
        frame.setAttack(person.getAttack());
        frame.setHp(person.getHp());
        frame.repaint();
    }

    @Override
    public void close() {
        frame.dispose();
    }
}
