package view;

import model.Place;
import model.Warrior;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;



public class MyFrame extends JFrame {

    private MyPanel panel;

    private JLabel nameL;
    private String name = "";

    private JLabel hpL;
    private int hp;

    private JLabel attackL;
    private int attack;

    private JLabel defenseL;
    private int defense;

    private JLabel helmetL;
    private int helmet;

    public MyFrame(String title, int width, int height) throws IOException {
        setVisible(true);
        setTitle(title);
//        setUndecorated(true);
        setSize(width + 300, height);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        panel = new MyPanel(width, height);


        JPanel labels = new JPanel(null);
//        labels.setSize(300, 600);
//        labels.setLayout(null);


//        JTextArea textArea = new JTextArea(
//                "This is an editable JTextArea. "
//        );
//        textArea.setLocation(10, 10);
//        labels.add(textArea);


        JLabel label = new JLabel("name");
        label.setBounds(50,50, 100,30);
        labels.add(label);

        nameL = new JLabel(name);
        nameL.setBounds(200,50, 100,30);
        labels.add(nameL);

        label = new JLabel("hp");
        label.setBounds(50,150, 100,30);
        labels.add(label);

        hpL = new JLabel(Integer.toString(hp));
        hpL.setBounds(200,150, 100,30);
        labels.add(hpL);

        label = new JLabel("armor");
        label.setBounds(50,250, 100,30);
        labels.add(label);

        attackL = new JLabel(Integer.toString(attack));
        attackL.setBounds(200,250, 100,30);
        labels.add(attackL);

        label = new JLabel("defense");
        label.setBounds(50,350, 100,30);
        labels.add(label);

        defenseL = new JLabel(Integer.toString(defense));
        defenseL.setBounds(200,350, 100,30);
        labels.add(defenseL);

        label = new JLabel("helmet");
        label.setBounds(50,450, 100,30);
        labels.add(label);

        helmetL = new JLabel(Integer.toString(helmet));
        helmetL.setBounds(200,450, 100,30);
        labels.add(helmetL);

        final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panel, labels);
        splitPane.setOneTouchExpandable(false);
        splitPane.setDividerLocation(800);
        splitPane.setEnabled(false);


        panel.setMinimumSize(new Dimension(800, 600));
        labels.setMinimumSize(new Dimension(300, 600));

//        splitPane.setPreferredSize(new Dimension(1000, 600));
        add(splitPane);
        repaint();


        setVisible(true);
//        setVisible(false);

    }

    public Place[][] getEnv() {
        return panel.getEnv();
    }

    public void setPersonName(String name) {
        this.name = name;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setHelmet(int helmet) {
        this.helmet = helmet;
    }

    @Override
    public void repaint() {
        nameL.setText(name);
        hpL.setText(Integer.toString(hp));
        attackL.setText(Integer.toString(attack));
        defenseL.setText(Integer.toString(defense));
        helmetL.setText(Integer.toString(helmet));
        super.repaint();
        setVisible(true);
    }
}
