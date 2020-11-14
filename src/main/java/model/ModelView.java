package model;

import view.MyView;

import java.awt.*;

public interface ModelView {
    public void registerView(MyView view);
    public void printModel();
}
