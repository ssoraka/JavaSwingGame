package view;

import controllers.AllController;
import z_main.Activator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.BiConsumer;

public class SimpleGUI extends JFrame {
    private AllController controllers;

    public SimpleGUI(AllController controllers) {
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

        buttonCreate.addActionListener(e -> createOrContinueMenu(controllers::createNewPersonAndStartGame));
        buttonContinue.addActionListener(e -> createOrContinueMenu(controllers::findPersonAndStartGame));
        buttonExit.addActionListener(e -> controllers.exit());
        repaint();
        setVisible(true);
    }

    private void createOrContinueMenu(BiConsumer<String, String> consumer) {
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
                    consumer.accept(login.getText(), password.getText());
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(null,
                            ex.getMessage(),
                            "Fatal error",
                            JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                closeMenu();
                Activator.startGame();
            }
        });

        repaint();
        setVisible(true);
    }

    private void closeMenu() {
        setVisible(false);
        dispose();
    }
}