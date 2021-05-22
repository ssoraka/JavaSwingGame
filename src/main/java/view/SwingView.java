package view;

import controllers.Actions;
import controllers.AllController;
import model.DAOException;
import model.DeadException;
import model.Dice;
import model.ModelView;
import model.items.Item;
import model.war.Clazz;
import model.war.Fighting;
import model.war.Warrior;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import static model.war.Warrior.*;


public class SwingView extends JFrame implements MyView {

    private AllController controller;
    private ModelView model;

    private MyPanel gamePanel;
    private JPanel textPanel;
    private KeyListener listener;

    private Map<String, JLabel> labels;

    private static int TEXT_WIDTH = 500;
    private static int TEXT_START_X = 20;
    private static int TEXT_START_Y = 20;
    private static int TEXT_STEP_X = 100;
    private static int TEXT_STEP_Y = 20;

    private static final String LOGGER = "logger";
    private static final String[] LABELS = {NAME, HP, LEVEL, EXP, ATTACK, DEFENSE, HELMET, ARMOR, WEAPON, HELM};

    private static final String TITLE = "SWINGY";
    private static final int GAME_PANEL_HEIGHT = 600;
    private static final int GAME_PANEL_WIDTH = 800;
    private static final int TEXT_PANEL_WIDTH = 500;
    private static final Dimension GAME_DIMENSIONS = new Dimension(GAME_PANEL_WIDTH + TEXT_PANEL_WIDTH, GAME_PANEL_HEIGHT);

    public SwingView(ModelView model, AllController controller) {
        this.controller = controller;
        this.model = model;

        gamePanel = new MyPanel(GAME_PANEL_WIDTH, GAME_PANEL_HEIGHT);
        gamePanel.setBounds(0, 0, GAME_PANEL_WIDTH, GAME_PANEL_HEIGHT);

        textPanel = createTextPane();
        textPanel.setBounds(GAME_PANEL_WIDTH + 10, 10, TEXT_WIDTH - 20, GAME_PANEL_HEIGHT - 40);

        listener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                Actions action = Actions.getAction(e.getKeyChar());
                if (action == Actions.EXIT) {
                    controller.exit();
                    return;
                }
                try {
                    if (controller.isMeetEnemy(action) && !confirm("Start fight?") && Dice.d2()) {
                        return;
                    } else {
                        if (controller.executeCommand(action)) {
                            getReward();
                            controller.moveWorld();
                            getReward();
                        }
                        refresh();
                    }
                } catch (DeadException ex) {
                    refresh();
                    if (confirm(ex.getMessage() + "\nrestart level?")) {
                        controller.continueGame();
                    } else {
                        controller.startMenu();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        };
    }

    private void getReward() {
        while (model.hasItems()) {
            Item item = model.getItem();
            if (confirm("Do you want equip " + item + "?")) {
                item.equip(model.getPlayer());
            }
        }
    }

    private boolean confirm(String message) {
        return JOptionPane.showConfirmDialog(null, message, "Warning", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    private void clearView() {
        removeKeyListener(listener);
        remove(gamePanel);
        remove(textPanel);

        getContentPane().removeAll();
        dispose();

        setTitle(TITLE);

        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    private JPanel createTextPane() {
        JPanel panel = new JPanel(null);
        panel.setSize(TEXT_PANEL_WIDTH - 20, GAME_PANEL_HEIGHT);
        panel.setBackground(Color.GRAY);

        labels = new HashMap<>();

        int y = TEXT_START_Y;
        for (String s : LABELS) {
            JLabel label = new JLabel(s);
            label.setBounds(TEXT_START_X, y, TEXT_STEP_X, TEXT_START_Y);
            panel.add(label);

            label = new JLabel(s);
            label.setBounds(TEXT_START_X + TEXT_STEP_X, y, TEXT_WIDTH - TEXT_STEP_X, TEXT_START_Y);
            panel.add(label);
            labels.put(s, label);

            y += TEXT_STEP_Y;
        }

        JLabel label = new JLabel(LOGGER);
//        label.setPreferredSize(new Dimension( TEXT_PANEL_WIDTH - 40,2000));
//
//        JScrollPane scrollFrame = new JScrollPane();
//        label.setAutoscrolls(true);
//        scrollFrame.add(label);
//        scrollFrame.setPreferredSize(new Dimension( TEXT_PANEL_WIDTH - 40,100));
//        scrollFrame.setBounds(10, y, TEXT_PANEL_WIDTH - 50, 300);
//        panel.add(scrollFrame);

        label.setBounds(20, y, TEXT_PANEL_WIDTH - 40, GAME_PANEL_HEIGHT);
        label.setVerticalAlignment(SwingConstants.TOP);
        panel.add(label);
        labels.put(LOGGER, label);

        return  panel;
    }

    public void updateField(String name, String value) {
        labels.get(name).setText(value);
    }


    @Override
    public void startMenu() {
        clearView();

        setLayout(new GridLayout(10,1));

        setBounds(0,0,400,400);
        setSize(400,400);

        JButton buttonCreate = new JButton("Начать сначала");
        JButton buttonContinue = new JButton("Продолжить");
        JButton buttonExit = new JButton("Выход");
        add(buttonCreate);
        add(buttonContinue);
        add(buttonExit);

        buttonCreate.addActionListener(e -> controller.createMenu());
        buttonContinue.addActionListener(e -> controller.continueGame());
        buttonExit.addActionListener(e -> controller.exit());
        repaint();
    }

    @Override
    public void createMenu() {
        clearView();

        JTextField login = new JTextField("", 5);
        JTextField password = new JTextField("", 5);
        JLabel labelLogin = new JLabel("login:");
        JLabel labelPassword = new JLabel("password:");
        JButton buttonConfirm = new JButton("Подтвердить");
        JButton buttonBack = new JButton("назад");
        ButtonGroup race = new ButtonGroup();

        setLayout(new GridLayout(10,1));
        add(labelLogin);
        add(login);
        add(labelPassword);
        add(password);

        for (Clazz c : Clazz.values()) {
            JRadioButton cap = new JRadioButton(c.name());
            cap.addActionListener(e -> controller.setClazz(c));
            add(cap);
            cap.setSelected(true);
            race.add(cap);
        }


        add(buttonConfirm);
        add(buttonBack);

        buttonBack.addActionListener(e -> controller.startMenu());
        buttonConfirm.addActionListener(e -> {
            try {
                controller.setLogin(login.getText());
                controller.setPassword(password.getText());
                controller.createNewPersonInGame();
                controller.startGame();
            } catch (DAOException ex) {
                JOptionPane.showMessageDialog(null,
                        ex.getMessage(),
                        "Authentication error",
                        JOptionPane.PLAIN_MESSAGE);
            }
        });

        repaint();
    }

    @Override
    public void continueGame() {
        try {
            controller.findPersonInGame();
            controller.startGame();
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(null,
                    ex.getMessage(),
                    "Authentication error",
                    JOptionPane.PLAIN_MESSAGE);
        }
    }

    @Override
    public void startGame() {
        clearView();

        setBounds(0,0,GAME_PANEL_WIDTH + TEXT_PANEL_WIDTH, GAME_PANEL_HEIGHT);
        setSize(GAME_DIMENSIONS);

        add(gamePanel);
        add(textPanel);
        addKeyListener(listener);

        refresh();
    }

    public void refresh() {
        model.fillEnvironment(gamePanel.getEnv());
        Warrior person = model.getPlayer();
        updateField(NAME, person.getName());
        updateField(HP, person.getHp() + "/" + person.maxHp());
        updateField(LEVEL, Integer.toString(person.getLevel()));
        updateField(EXP, person.getExperience() + "/" + person.getExperienceForNextLevel());
        updateField(DEFENSE, Integer.toString(person.getDefense()));
        updateField(HELMET, Integer.toString(person.getHelmet()));
        updateField(ATTACK, Integer.toString(person.getAttack()));

        updateField(ARMOR, person.getArmor().toString());
        updateField(WEAPON, person.getWeapon().toString());
        updateField(HELM, person.getHelm().toString());

        updateField(LOGGER, Fighting.getHtmlLog());

        repaint();
    }

    @Override
    public void destroy() {
        clearView();
        setVisible(false);
//        enableEvents(WindowEvent.WINDOW_CLOSING);
    }
}
