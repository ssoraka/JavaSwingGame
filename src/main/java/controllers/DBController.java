package controllers;

import model.ModelController;

public class DBController implements Controller {
    private ModelController model;

    public DBController(ModelController model) {
        this.model = model;
    }

    @Override
    public void execute(ActionBuilder action) {
        switch (action.getAction()) {
            case CREATE: model.createNewPersonAndStartGame(action.getLogin(), action.getLogin()); break;
            default:
                break;
        }
    }
}
