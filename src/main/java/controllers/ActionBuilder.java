package controllers;

public class ActionBuilder {
    public static enum Action {
        NON,
        TEXT,

        CREATE,
        LOGIN,
        EXIT,

        MOVE_LEFT,
        MOVE_RIGHT,
        MOVE_UP,
        MOVE_DOWN
    }

    private Action action;
    private String message;
    private String errorMessage;
    private String login;
    private String password;

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

    public ActionBuilder setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public ActionBuilder setLogin(String login) {
        this.login = login;
        return this;
    }

    public ActionBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public Action getAction() {
        return action;
    }

    public String getMessage() {
        return message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
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
