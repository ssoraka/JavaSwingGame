package view;

import model.Place;
import model.PlaceHolder;
import model.Types;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MyPanel extends JPanel {
    private final static int CELL_SIZE = 32;
    private static final Color TREE_COLOR = new Color(15, 167, 136);
    private static final Color GREEN_COLOR = new Color(105, 187, 106);
    private static final Place OUT = new Place(new PlaceHolder(Types.BOUNDARY), Types.BLACK);

    private Place[][] env;
    private TexturePaint tree;
    private TexturePaint salamander;
    private TexturePaint capybara;
    private TexturePaint stone;

    public MyPanel(int width, int height){
        setSize(width, height);
        width = width / CELL_SIZE;
        height = height / CELL_SIZE;
        env = new Place[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                env[i][j] = OUT;
            }
        }

        loadImage();
    }

    private void loadImage() {
        try {
            BufferedImage image = ImageIO.read(getClass().getResource("/tree.png"));
            tree = new TexturePaint(image, new Rectangle(0, 0, 32, 32));
            image = ImageIO.read(getClass().getResource("/stone.png"));
            stone = new TexturePaint(image, new Rectangle(0, 0, 32, 32));
            image = ImageIO.read(getClass().getResource("/capybara.png"));
            capybara = new TexturePaint(image, new Rectangle(0, 0, 32, 32));
            image = ImageIO.read(getClass().getResource("/salamander.png"));
            salamander = new TexturePaint(image, new Rectangle(0, 0, 32, 32));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Could not load images", "Error", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(ex);
        }
//        setOpaque(false);
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
                printPlane(g2, env[i][j], j * CELL_SIZE, i * CELL_SIZE);
                printObject(g2, env[i][j], j * CELL_SIZE, i * CELL_SIZE);
            }
        }
    }


    private void printPlane(Graphics2D g2, Place place, int x, int y) {
        int size = 2;

        // в перспективе тут должны быть отмечены свойства земли, на которой расположен объект
        switch (place.getType()) {
            case BLOOD: g2.setColor(Color.RED); break;
            case GREEN: g2.setColor(GREEN_COLOR); break;
            case WATER: g2.setColor(Color.blue); break;
            case BLACK: g2.setColor(Color.black); break;
            case EARTH: g2.setColor(Color.darkGray); break;
            default:
                g2.setColor(Color.white);
        }
        g2.drawRect(x + size / 2, y + size / 2, CELL_SIZE - size, CELL_SIZE - size);
        g2.fillRect(x + size / 2, y + size / 2, CELL_SIZE - size, CELL_SIZE - size);
    }

    private void printObject(Graphics2D g2, Place place, int x, int y) {
        int size = 2;

        switch (place.getObject().getTypes()) {
            case STONE: {
                g2.setPaint(stone);
                g2.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                return;
            }
            case PlAYER: {
                g2.setPaint(capybara);
                g2.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                return;
            }
            case ANIMAL: {
                g2.setPaint(salamander);
                g2.fillRect(x + 2, y + 2, 28, 28);
                return;
            }
            case BOUNDARY: g2.setColor(Color.black); break;
            case TREE: {
                g2.setPaint(tree);
                g2.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                return;
            }
            default:
                break;
        }
        g2.fillOval(x + size / 2, y + size / 2, CELL_SIZE - size, CELL_SIZE - size);
    }
}
