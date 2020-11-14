package controllers;

public class ActionBuilder {
    public static enum Action {
        NON,
        TEXT,

        MOVE_LEFT,
        MOVE_RIGHT,
        MOVE_UP,
        MOVE_DOWN
    }

    private Action action;
    private String message;

    public ActionBuilder() {
        this.action = Action.NON;
    }

    public ActionBuilder setAction(Action action) {
        this.action = action;
        return this;
    }

    public ActionBuilder setMessage(String message) {
        this.message = message;
        return this;
    }

    public Action getAction() {
        return action;
    }

    public String getMessage() {
        return message;
    }
    
    public ActionBuilder build() {
        if (action == Action.TEXT && message == null)
            action = Action.NON;
        return this;
    }

    @Override
    public String toString() {
        return String.format("%s-%s", action.toString(), message);
    }
}
