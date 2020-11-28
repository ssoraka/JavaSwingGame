package controllers;

import model.Level;
import model.ModelController;

import java.awt.*;

public class MoveController implements Controller {
    private ModelController model;

    public MoveController(ModelController model) {
        this.model = model;
    }

    @Override
    public void execute(ActionBuilder action) {
        switch (action.getAction()) {
            case MOVE_DOWN : model.tryMovePlayer(Level.DOWN); break;
            case MOVE_UP : model.tryMovePlayer(Level.UP); break;
            case MOVE_LEFT: model.tryMovePlayer(Level.LEFT); break;
            case MOVE_RIGHT: model.tryMovePlayer(Level.RIGHT); break;
            default:
                break;
        }
    }
}
