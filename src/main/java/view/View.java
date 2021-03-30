package view;

import controllers.AllController;
import model.ModelView;

public class View {
    private AllController controller;
    private ModelView model;
    private ViewType type;
    private MyView view;
    private boolean isGameStarted;


    public View(ViewType type, ModelView model, AllController controller) {
        this.type = type;
        this.model = model;
        this.controller = controller;
        createView();
    }

    private void createView() {
        switch (type) {
            case SWING: view = new SwingView(model, controller); break;
            case TERMINAL: view = new TerminalView(model, controller); break;
        }
    }

    public void changeView() {
        view.destroy();
        type = type.next();
        createView();
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
        isGameStarted = true;
    }

    public void refresh() {
        if (isGameStarted) {
            view.refresh();
        }
//        Thread.getAllStackTraces().keySet().forEach((t) -> System.out.println(t.getName()));
    }
}
