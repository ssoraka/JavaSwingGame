package view;

import model.Place;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class MyFrame extends JFrame {

    private MyPanel panel;
    private Map<String, JLabel> labels;

    private static int TEXT_WIDTH = 300;
    private static int TEXT_START_X = 50;
    private static int TEXT_START_Y = 50;
    private static int TEXT_STEP_X = 100;
    private static int TEXT_STEP_Y = 30;

    static String NAME = "name";
    static String HP = "hp";
    static String ATTACK = "attack";
    static String DEFENSE = "defense";
    static String HELMET = "helmet";

    private static String[] LABELS = {NAME, HP, ATTACK, DEFENSE, HELMET};

    public MyFrame(String title, int width, int height) throws IOException {
        setVisible(true);
        setTitle(title);
//        setUndecorated(true);
        setSize(width + 300, height);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        panel = new MyPanel(width, height);
        panel.setBounds(0, 0, width, height);
        add(panel);

        add(createTextPane(width, height));

        repaint();
        setVisible(true);
    }


    private JPanel createTextPane(int width, int height) {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.GRAY);
        panel.setBounds(width, 0, width + TEXT_WIDTH, height);

        labels = new HashMap<>();

        int y = TEXT_START_Y;
        for (String s : LABELS) {
            JLabel label = new JLabel(s);
            label.setBounds(width + TEXT_START_X, y, width + TEXT_STEP_X, TEXT_START_Y);
            panel.add(label);

            label = new JLabel(s);
            label.setBounds(width + TEXT_START_X + TEXT_STEP_X, y, width + TEXT_STEP_X, TEXT_START_Y);
            panel.add(label);

            labels.put(s, label);

            y += TEXT_STEP_Y;
        }

        return  panel;
    }


    public Place[][] getEnv() {
        return panel.getEnv();
    }

    public void updateField(String name, String value) {
        labels.get(name).setText(value);
    }

    @Override
    public void repaint() {
        super.repaint();
        setVisible(true);
    }
}
