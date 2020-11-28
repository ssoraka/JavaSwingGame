package view;

import javax.swing.*;
import java.awt.*;

public class MyPanel extends JPanel {
    private final static int CELL_SIZE = 32;

    private Point point;
    private Point windowPos;

    public void setPoint(Point point) {
        this.point = point;
    }

    public MyPanel() {
        windowPos = new Point(0, 0);
        //point = windowPos;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        windowPos.x = point.x * CELL_SIZE;
        windowPos.y = point.y * CELL_SIZE;
        g2.setColor(Color.blue);
//        g2.drawLine(windowPos.x + 16, windowPos.y + 16, windowPos.x + 16, windowPos.y + 16);
        g2.drawRect(windowPos.x, windowPos.y, CELL_SIZE, CELL_SIZE);
        g2.fillRect(windowPos.x, windowPos.y, CELL_SIZE, CELL_SIZE);

    }
}
