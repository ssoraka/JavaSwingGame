package model;

import view.MyView;

import java.awt.*;

public interface ModelController {
    public void tryMovePlayer(Point shift);
    public void printMessage(String message);
}
