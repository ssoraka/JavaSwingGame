package view;

import controllers.ActionBuilder;
import controllers.Controller;
import model.ModelView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleGUI extends JFrame {
    private ModelView model;
    private Controller controllers;

    public SimpleGUI(ModelView model, Controller controllers) {
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

        buttonCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "персонаж создан, типа...",
                        "Output",
                        JOptionPane.PLAIN_MESSAGE);
                loginMenu();
            }
        });

        buttonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controllers.execute(new ActionBuilder().setAction(ActionBuilder.Action.EXIT).build());
            }
        });
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

        buttonConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controllers.execute(new ActionBuilder()
                            .setAction(ActionBuilder.Action.CREATE)
                            .setLogin(login.getText())
                            .setPassword(password.getText())
                            .build());
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(null,
                            ex.getMessage(),
                            "Fatal error",
                            JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                JOptionPane.showMessageDialog(null,
                        "Подтверждено, начинаем игру ...",
                        "Output",
                        JOptionPane.PLAIN_MESSAGE);
            }
        });

        buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startMenu();
            }
        });
        repaint();
        setVisible(true);
    }
}