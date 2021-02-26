package view;

import controllers.AllController;
import model.ModelView;
import model.Place;
import z_main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleGUI extends JFrame {
    private ModelView model;
    private AllController controllers;
//    private MyPanel panel;

    public SimpleGUI(ModelView model, AllController controllers) {
        this.model = model;
        this.controllers = controllers;
        this.setBounds(100,100,400,400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startMenu();
    }


    private void startMenu() {
        JButton buttonCreate = new JButton("Начать сначала");
        JButton buttonContinue = new JButton("Продолжить");
        JButton buttonExit = new JButton("Выход");


        Container container = this.getContentPane();
        container.removeAll();
        container.setLayout(new GridLayout(10,1));
        container.add(buttonCreate);
        container.add(buttonContinue);
        container.add(buttonExit);

        buttonCreate.addActionListener(e -> loginMenu());
        buttonExit.addActionListener(e -> controllers.exit(null));
        repaint();
        setVisible(true);
    }

    private void loginMenu() {
        JTextField login = new JTextField("", 5);
        JTextField password = new JTextField("", 5);
        JLabel labelLogin = new JLabel("login:");
        JLabel labelPassword = new JLabel("password:");
        JButton buttonConfirm = new JButton("Подтвердить");
        JButton buttonBack = new JButton("назад");

        Container container = this.getContentPane();
        container.removeAll();
        container.setLayout(new GridLayout(10,1));
        container.add(labelLogin);
        container.add(login);
        container.add(labelPassword);
        container.add(password);
        container.add(buttonConfirm);
        container.add(buttonBack);

        buttonBack.addActionListener(e -> startMenu());
        buttonConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controllers.createNewPersonAndStartGame(login.getText(), password.getText());
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(null,
                            ex.getMessage(),
                            "Fatal error",
                            JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                closeMenu();
                Main.startGame();
            }
        });

        repaint();
        setVisible(true);
    }

    private void closeMenu() {
        startMenu();
        setVisible(false);
    }
}