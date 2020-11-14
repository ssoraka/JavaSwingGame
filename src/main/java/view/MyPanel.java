package view;

import javax.swing.*;
import java.awt.*;

public class MyPanel extends JPanel {
    private Point point;

    public void setPoint(Point point) {
        this.point = point;
    }

    public MyPanel() {}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.blue);
        if (point != null) {
            g2.drawLine(point.x, point.y, point.x, point.y);
            g2.drawRect(point.x - 10, point.y - 10, 20, 20);
        }
    }
}
