package model;

import java.awt.*;

public class SomeThing {
    private Types type;
    private Point pos;

    public SomeThing(Types type, int x, int y) {
        this.type = type;
        pos = new Point(x, y);
    }

    public int getX() {
        return pos.x;
    }

    public int getY() {
        return pos.y;
    }

    public Point getPos() {
        return pos;
    }
}
