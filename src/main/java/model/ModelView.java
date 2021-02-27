package model;

import view.MyView;

import java.awt.Point;
import java.util.List;

public interface ModelView {
    public boolean isGameRun();
    public List<String> getMessages();
    public void fillEnvironment(Place[][] env);
}
