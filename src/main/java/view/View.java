package view;

import controllers.AllController;
import controllers.AllController2;
import model.ModelView;

public class View implements MyView2{
    private AllController2 controller;
    private ModelView model;
    private ViewType type;
    private MyView2 view;
    private boolean isGameStarted;


    public View(ViewType type, ModelView model, AllController2 controller) {
        this.type = type;
        this.model = model;
        this.controller = controller;

        switch (type) {
            case SWING: view = new SwingView2(model, controller); break;
            case TERMINAL: view = new TerminalView2(model, controller); break;
        }
    }


    public void changeView() {
        view.destroy();
        switch (type) {
            case TERMINAL: view = new SwingView2(model, controller); break;
            case SWING: view = new TerminalView2(model, controller); break;
        }
    }


    @Override
    public void startMenu() {
        view.startMenu();
    }

    @Override
    public void createMenu() {
        view.createMenu();
    }

    @Override
    public void continueGame() {
        view.continueGame();
    }

    @Override
    public void startGame() {
        view.startGame();
        isGameStarted = true;
    }

    @Override
    public void refresh() {
        if (isGameStarted) {
            view.refresh();
        }
    }

    @Override
    public void destroy() {
        view.destroy();
    }
}
