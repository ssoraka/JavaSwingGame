package view;

import model.Place;
import model.Types;

import javax.swing.*;
import java.awt.*;

public class MyPanel extends JPanel {
    private final static int CELL_SIZE = 32;
    private static final Color TREE_COLOR = new Color(15, 167, 136);
    private static final Color GREEN_COLOR = new Color(105, 187, 106);

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
                printObject(g2, env[i][j], j * CELL_SIZE, i * CELL_SIZE);
            }
        }
    }


    private void printObject(Graphics2D g2, Place place, int x, int y){
        int size = 2;

        switch (place.getType()) {
            case FLAME: g2.setColor(Color.RED); break;
            case GREEN: g2.setColor(GREEN_COLOR); break;
            case WATER: g2.setColor(Color.blue); break;
            case BLACK: g2.setColor(Color.black); break;
            case EARTH: g2.setColor(Color.darkGray); break;
            default:
                g2.setColor(Color.white);
        }
        g2.drawRect(x + size / 2, y + size / 2, CELL_SIZE - size, CELL_SIZE - size);
        g2.fillRect(x + size / 2, y + size / 2, CELL_SIZE - size, CELL_SIZE - size);


        switch (place.getObject().getTypes()) {
            case STONE: g2.setColor(Color.gray); break;
            case PlAYER: g2.setColor(Color.blue); size = 6; break;
            case ANIMAL: g2.setColor(Color.red); size = 10; break;
            case BOUNDARY: g2.setColor(Color.black); break;
            case TREE: g2.setColor(TREE_COLOR); break;
            default:
                break;
        }
        g2.fillOval(x + size / 2, y + size / 2, CELL_SIZE - size, CELL_SIZE - size);
    }
}
