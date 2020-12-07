package view;

import model.Place;

import javax.swing.*;
import java.io.IOException;

public class MyFrame extends JFrame {

    private MyPanel panel;

    public MyFrame(String title, int width, int height) throws IOException {
        setVisible(true);
        setTitle(title);
//        setUndecorated(true);
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new MyPanel(width, height);
        add(panel);


//        setVisible(false);

    }

    public Place[][] getEnv() {
        return panel.getEnv();
    }

    @Override
    public void repaint() {
        super.repaint();
        setVisible(true);
    }
}
