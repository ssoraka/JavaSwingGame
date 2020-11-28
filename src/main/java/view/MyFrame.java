package view;

import model.Place;

import javax.swing.*;

public class MyFrame extends JFrame {

    private MyPanel panel;

    public MyFrame(String title, int width, int height) {
        setVisible(true);
        setTitle(title);
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new MyPanel(width, height);
        add(panel);
    }

    public Place[][] getEnv() {
        return panel.getEnv();
    }

    @Override
    public void repaint() {
        super.repaint();
    }
}
