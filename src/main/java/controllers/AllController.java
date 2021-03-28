package controllers;

import model.Level;
import model.ModelController;
import view.View;

public class AllController {
    private ModelController model;
    private View view;
    private String login = "";
    private String password = "";

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

    public void createNewPersonInGame() {
        if (password.isEmpty() || login.isEmpty()) {
            throw new RuntimeException("Ошибка: Введите логин и пароль");
        }
        model.createNewPersonAndStartGame(login, password);
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

    public void executeCommand(Actions action) {
        switch (action) {
            case MOVE_UP : model.tryMovePlayer(Level.UP); break;
            case MOVE_DOWN: model.tryMovePlayer(Level.DOWN); break;
            case MOVE_LEFT: model.tryMovePlayer(Level.LEFT); break;
            case MOVE_RIGHT: model.tryMovePlayer(Level.RIGHT); break;
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
