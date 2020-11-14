package model;

import view.MyView;

import java.awt.Point;
import java.util.List;

public interface ModelView {
    public void registerView(MyView view);
    public Point getPlayerPos();
    public List<String> getMessages();
}
