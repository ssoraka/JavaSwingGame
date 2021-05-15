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
        model.createNewPersonAndStartGame(login, password, clazz);
    }

    public void findPersonInGame() {
        if (password.isEmpty() || login.isEmpty()) {
            throw new RuntimeException("Ошибка: Введите логин и пароль");
        }
        model.findPersonAndStartGame(login, password);
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

    public void executeCommand(Actions action) {
        switch (action) {
            case MOVE_UP : model.tryMovePlayer(Level.UP); break;
            case MOVE_DOWN: model.tryMovePlayer(Level.DOWN); break;
            case MOVE_LEFT: model.tryMovePlayer(Level.LEFT); break;
            case MOVE_RIGHT: model.tryMovePlayer(Level.RIGHT); break;
            case DONT_MOVE: model.tryMovePlayer(Level.HERE); break;
            case CHANGE_VIEW: view.changeView(); startGame(); break;
            case EXIT: exit(); break;
            default:
                break;
        }
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

    public void startGame() {
        view.startGame();
    }
}
