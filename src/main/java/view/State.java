package view;

public class State {
    private Stage stage;
    private ViewType viewType;

    private String login;
    private String password;

    public State(ViewType viewType) {
        this.viewType = viewType;
        stage = Stage.START;
        password = "";
        login = "";
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public boolean changeStage(Stage stage) {
        if (this.stage.equals(stage)) {
            return false;
        }
        switch (stage) {
            case PASSWORD: if (login.isEmpty()) return false;
            case CONTINUE:
            case CREATE: if (login.isEmpty() || password.isEmpty()) return false;
        }
        this.stage = stage;
        return true;
    }

    public ViewType getViewType() {
        return viewType;
    }

    public void setViewType(ViewType viewType) {
        this.viewType = viewType;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
