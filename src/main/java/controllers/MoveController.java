package controllers;

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
            case MOVE_DOWN : model.tryMovePlayer(new Point(0, 1)); break;
            case MOVE_UP : model.tryMovePlayer(new Point(0, -1)); break;
            case MOVE_LEFT: model.tryMovePlayer(new Point(-1, 0)); break;
            case MOVE_RIGHT: model.tryMovePlayer(new Point(1, 0)); break;
            default:
                break;
        }
    }
}
