package controllers;

import model.Level;
import model.ModelController;

public class AllController {
    private ModelController model;

    public AllController(ModelController model) {
        this.model = model;
    }

    public void createNewPersonAndStartGame(String login, String password) {
        model.createNewPersonAndStartGame(login, password);
    }

    public void findPersonAndStartGame(String login, String password) {
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
            case EXIT: exit(); break;
            default:
                break;
        }
    }
}
