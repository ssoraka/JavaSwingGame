package controllers;

import model.Level;
import model.ModelController;
import model.war.Clazz;
import view.View;

public class AllController {
    private ModelController model;
    private View view;
    private String login = "";
    private String password = "";
    private Clazz clazz = Clazz.CAPYBARA;

    public AllController(ModelController model) {
        this.model = model;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }

    public void createNewPersonInGame() {
        if (password.isEmpty() || login.isEmpty()) {
            throw new RuntimeException("Ошибка: Введите логин и пароль");
        }
        model.createNewPerson(login, clazz);
    }

    public void findPersonInGame() {
        if (password.isEmpty() || login.isEmpty()) {
            throw new RuntimeException("Ошибка: Введите логин и пароль");
        }
        model.findPerson(login, password);
    }

    public void exit() {
        model.exit();
    }

    public boolean isMeetEnemy(Actions action) {
        switch (action) {
            case MOVE_UP : return model.isMeetEnemy(Level.UP);
            case MOVE_DOWN: return model.isMeetEnemy(Level.DOWN);
            case MOVE_LEFT: return model.isMeetEnemy(Level.LEFT);
            case MOVE_RIGHT: return model.isMeetEnemy(Level.RIGHT);
            default:
                return false;
        }
    }

    public boolean executeCommand(Actions action) {
        switch (action) {
            case MOVE_UP : return model.movePlayer(Level.UP);
            case MOVE_DOWN: return model.movePlayer(Level.DOWN);
            case MOVE_LEFT: return model.movePlayer(Level.LEFT);
            case MOVE_RIGHT: return model.movePlayer(Level.RIGHT);
            case CHANGE_VIEW: view.changeView(); startGame(); break;
            case EXIT: exit(); break;
        }
        return false;
    }

    public void moveWorld() {
        model.moveWorld();
    }

    public void startMenu() {
        view.startMenu();
    }

    public void createMenu() {
        view.createMenu();
    }

    public void continueGame() {
        view.continueGame();
    }

    public void watchHero() {
        view.watchHero();
    }

    public void startGame() {
        model.startGame(login, password);
        view.startGame();
    }
}
