package model;

import view.MyView;

import java.awt.Point;
import java.util.List;

public interface ModelView {
    public AppStatus getStatus();
    public List<String> getMessages();
    public void fillEnvironment(Place[][] env);
}
