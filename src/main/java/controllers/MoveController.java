package controllers;

import model.ModelController;
import model.TestModel;

import java.awt.*;

public class MoveController implements Controller {
    private ModelController model;

    public MoveController(ModelController model) {
        this.model = model;
    }

    @Override
    public void execute(ActionBuilder action) {
        switch (action.getAction()) {
            case MOVE_DOWN : model.tryMovePlayer(TestModel.DOWN); break;
            case MOVE_UP : model.tryMovePlayer(TestModel.UP); break;
            case MOVE_LEFT: model.tryMovePlayer(TestModel.LEFT); break;
            case MOVE_RIGHT: model.tryMovePlayer(TestModel.RIGHT); break;
            default:
                break;
        }
    }
}
