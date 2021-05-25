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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JScrollPane logPanel;
    private KeyListener listener;

    private JTextField login;
    private JTextField password;
    private JButton buttonConfirm;
    private JButton buttonBack;

    private ActionListener createListener;
    private ActionListener startListener;
    private ActionListener exitListener;
    private ActionListener startGameListener;
    private ActionListener continueListener;

    private Map<String, JLabel> labels;

    private static int TEXT_WIDTH = 500;
    private static int TEXT_START_X = 20;
    private static int TEXT_START_Y = 20;
    private static int TEXT_STEP_X = 100;
    private static int TEXT_STEP_Y = 20;

    private static final String LOGGER = "logger";
    private static final String[] LABELS = {NAME, HP, LEVEL, EXP, ATTACK, DEFENSE, HIT_POINTS, ARMOR, WEAPON, HELM};

    private static final String TITLE = "SWINGY";
    private static final int GAME_PANEL_HEIGHT = 600;
    private static final int GAME_PANEL_WIDTH = 800;
    private static final int TEXT_PANEL_WIDTH = 500;
    private static final Dimension GAME_DIMENSIONS = new Dimension(GAME_PANEL_WIDTH + TEXT_PANEL_WIDTH, GAME_PANEL_HEIGHT);

    public SwingView(ModelView model, final AllController controller) {
        this.controller = controller;
        this.model = model;

        gamePanel = new MyPanel(GAME_PANEL_WIDTH, GAME_PANEL_HEIGHT);
        gamePanel.setBounds(0, 0, GAME_PANEL_WIDTH, GAME_PANEL_HEIGHT);

        labels = new HashMap<>();

        textPanel = createTextPane();
        textPanel.setBounds(GAME_PANEL_WIDTH + 10, 10, TEXT_WIDTH - 20, 230);

        logPanel = createLogPane();
        logPanel.setBounds(GAME_PANEL_WIDTH + 10, 250, TEXT_WIDTH - 20, 320);

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
                        controller.startGame();
                    } else {
                        controller.startMenu();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        };


        createListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.createMenu();
            }
        };
        startListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.startMenu();
            }
        };
        exitListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.exit();
            }
        };
        startGameListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.startGame();
            }
        };
        continueListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.continueGame();
            }
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

        int y = TEXT_START_Y;
        for (String s : LABELS) {
            JLabel label = new JLabel(s);
            label.setBounds(TEXT_START_X, y, TEXT_STEP_X, TEXT_START_Y);
            panel.add(label);

            label = new JLabel(s);
            label.setBounds(TEXT_START_X + TEXT_STEP_X, y, TEXT_WIDTH - 2 * TEXT_START_X - TEXT_STEP_X, TEXT_START_Y);
            panel.add(label);
            labels.put(s, label);

            y += TEXT_STEP_Y;
        }
        return  panel;
    }

    private JScrollPane createLogPane() {
        JLabel label = new JLabel(LOGGER);
        label.setVerticalAlignment(SwingConstants.TOP);
        labels.put(LOGGER, label);

        JScrollPane scroller = new JScrollPane(label, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroller.setBackground(Color.GRAY);
        scroller.getViewport().setBackground(Color.GRAY);

        return scroller;
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

        JButton buttonCreate = new JButton("Try again");
        JButton buttonContinue = new JButton("Continue");
        JButton buttonExit = new JButton("Exit");
        add(buttonCreate);
        add(buttonContinue);
        add(buttonExit);

        buttonCreate.addActionListener(createListener);
        buttonContinue.addActionListener(continueListener);
        buttonExit.addActionListener(exitListener);
        repaint();
    }

    @Override
    public void createMenu() {
        clearView();

        createLoginPassword();

        final AllController controller = this.controller;
        ButtonGroup race = new ButtonGroup();
        for (final Clazz c : Clazz.values()) {
            JRadioButton cap = new JRadioButton(c.name());
            cap.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    controller.setClazz(c);
                }
            });
            cap.setBounds(30 + 200 * (c.ordinal() % 2), 180 + 50 * ((c.ordinal() < 2) ? 0:1), 200, 50);
            add(cap);
            cap.setSelected(true);
            race.add(cap);
        }

        createConfirmBack();
        buttonBack.addActionListener(startListener);
        buttonConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.setLogin(login.getText());
                    controller.setPassword(password.getText());
                    controller.createNewPersonInGame();
                    controller.watchHero();
                } catch (DAOException ex) {
                    JOptionPane.showMessageDialog(null,
                            ex.getMessage(),
                            "Authentication error",
                            JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        repaint();
    }

    private void createLoginPassword() {
        login = new JTextField("", 5);
        login.setBounds(150, 50, 250, 50);
        add(login);

        password = new JTextField("", 5);
        password.setBounds(150, 100, 250, 50);
        add(password);

        JLabel labelLogin = new JLabel("login:");
        labelLogin.setBounds(20, 50, 200, 50);
        add(labelLogin);

        JLabel labelPassword = new JLabel("password:");
        labelPassword.setBounds(20, 100, 200, 50);
        add(labelPassword);
    }

    private void createConfirmBack() {
        buttonConfirm = new JButton("Accept");
        buttonConfirm.setBounds(200, 300, 200, 50);
        add(buttonConfirm);

        buttonBack = new JButton("Back");
        buttonBack.setBounds(0, 300, 200, 50);
        add(buttonBack);
    }

    @Override
    public void continueGame() {
        clearView();

        createLoginPassword();
        createConfirmBack();
        buttonBack.addActionListener(startListener);
        buttonConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.setLogin(login.getText());
                    controller.setPassword(password.getText());
                    controller.findPersonInGame();
                    controller.watchHero();
                } catch (DAOException ex) {
                    JOptionPane.showMessageDialog(null,
                            ex.getMessage(),
                            "Authentication error",
                            JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        repaint();
    }

    @Override
    public void watchHero() {
        clearView();

        JLabel label = new JLabel("HERO:");
        label.setBounds(40, 10, 200, 50);
        add(label);

        setBounds(0,0,400,400);
        setSize(400,400);

        textPanel.setBounds(20, 50, 400 - 40, 230);
        textPanel.setBackground(this.getBackground());

        add(textPanel);

        createConfirmBack();
        buttonBack.addActionListener(startListener);
        buttonConfirm.addActionListener(startGameListener);

        refresh();
    }

    @Override
    public void startGame() {
        clearView();

        setBounds(0,0,GAME_PANEL_WIDTH + TEXT_PANEL_WIDTH, GAME_PANEL_HEIGHT);
        setSize(GAME_DIMENSIONS);

        textPanel.setBounds(GAME_PANEL_WIDTH + 10, 10, TEXT_WIDTH - 20, 230);
        textPanel.setBackground(Color.GRAY);
        logPanel.setBounds(GAME_PANEL_WIDTH + 10, 250, TEXT_WIDTH - 20, 320);

        add(gamePanel);
        add(textPanel);
        add(logPanel);
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
        updateField(HIT_POINTS, Integer.toString(person.getHitPoints()));
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
    }
}
