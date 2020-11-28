package view;

import model.Place;
import model.Types;

import javax.swing.*;
import java.awt.*;

public class MyPanel extends JPanel {
    private final static int CELL_SIZE = 32;

    private Place[][] env;

    public MyPanel(int width, int height) {
        width = width / CELL_SIZE;
        height = height / CELL_SIZE;
        env = new Place[height][width];
    }

    public Place[][] getEnv() {
        return env;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

//        g2.drawLine(windowPos.x + 16, windowPos.y + 16, windowPos.x + 16, windowPos.y + 16);

        for (int i = 0; i < env.length; i++) {
            for (int j = 0; j < env[0].length; j++) {
                printObject(g2, env[i][j].getObject().getTypes(), j * CELL_SIZE, i * CELL_SIZE);
            }
        }
    }


    public void printObject(Graphics2D g2, Types type, int x, int y){
        switch (type) {
            case STONE: g2.setColor(Color.gray); break;
            case PlAYER: g2.setColor(Color.blue); break;
            case ANIMAL: g2.setColor(Color.red); break;
            case BOUNDARY: g2.setColor(Color.black); break;
            case TREE: g2.setColor(Color.green); break;
            default:
                g2.setColor(Color.white);
        }
        g2.drawRect(x, y, CELL_SIZE, CELL_SIZE);
        g2.fillRect(x, y, CELL_SIZE, CELL_SIZE);
    }
}
