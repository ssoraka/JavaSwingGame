package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Level {
    final static public Point LEFT = new Point(-1, 0);
    final static public Point RIGHT = new Point(1, 0);
    final static public Point UP = new Point(0, -1);
    final static public Point DOWN = new Point(0, 1);

    final static private SomeThing EMPTY = new SomeThing(Types.EMPTY, -1, -1);
    final static private SomeThing BOUNDARY = new SomeThing(Types.BOUNDARY, -1, -1);
    final static private Place OUT = new Place(BOUNDARY, Types.BLACK);


    private int width;
    private int height;
    private Place[][] map;
    private List<SomeThing> animals;
    private SomeThing player;

    private Random random;

    // надо сделать чтение карты с файла
    public Level(int width, int height) {
        this.width = width;
        this.height = height;

        map = new Place[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                map[i][j] = new Place(EMPTY);
            }
        }
        random = new Random();
        animals = new ArrayList<>();
        initMapByRandomValue();
    }

    public SomeThing getPlayer() {
        return player;
    }

    public void setPlayer(SomeThing player) {
        this.player = player;
        insertOnMap(player);
    }

    public void initMapByRandomValue() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int tmp = random.nextInt(100);
                if (tmp < 3) {
                    animals.add(new SomeThing(Types.ANIMAL, j, i));
                    insertOnMap(animals.get(animals.size() - 1));
                } else if (tmp < 7) {
                    insertOnMap(new SomeThing(Types.STONE, j, i));
                } else if (tmp < 10) {
                    insertOnMap(new SomeThing(Types.TREE, j, i));
                }
            }
        }
    }

    private void insertOnMap(SomeThing object, int x, int y) {
        if (y >= 0 && y < height && x >= 0 && x < width)
            map[y][x].setObject(object);
    }

    public void insertOnMap(SomeThing object) {
        int x = object.getX();
        int y = object.getY();
        insertOnMap(object, x, y);
    }

    public Place getPlace(int x, int y) {
        if (y >= 0 && y < height && x >= 0 && x < width)
            return map[y][x];
        return OUT;
    }

    public void fillEnvironment(Place[][] env) {
        int height = env.length;
        int width = env[0].length;
        Point center = player.getPos();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int y = center.y + i - height / 2;
                int x = center.x + j - width / 2;

                env[i][j] = getPlace(x, y);
            }
        }
    }

    public boolean isPlaceEmpty(int x, int y) {
        if (y < 0 || y >= height || x < 0 || x >= width)
            return false;
        if (map[y][x].getObject() != EMPTY)
            return false;
        return true;
    }

    public void tryMoveObject(SomeThing object, Point shift)  {
        if (isPlaceEmpty(object.getX() + shift.x, object.getY() + shift.y)) {
            Point pos = object.getPos();
            insertOnMap(EMPTY, pos.x, pos.y);
            pos.translate(shift.x, shift.y);
            insertOnMap(object);
        }
    }

    public void moveAnimals() {
        for (SomeThing animal : animals) {
            switch (random.nextInt(5)) {
                case 0 : tryMoveObject(animal, UP); break;
                case 1 : tryMoveObject(animal, DOWN); break;
                case 2 : tryMoveObject(animal, LEFT); break;
                case 3 : tryMoveObject(animal, RIGHT); break;
                default:
                    break;
            }
        }
    }
}
