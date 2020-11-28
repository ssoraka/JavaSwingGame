package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Level {
    final static private SomeThing EMPTY = new SomeThing(Types.EMPTY, -1, -1);
    final static private SomeThing BOUNDARY = new SomeThing(Types.BOUNDARY, -1, -1);
    final static private Place OUT = new Place(EMPTY);


    private int width;
    private int height;
    private Place[][] map;
    private List<SomeThing> animals;

    private Random random;

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

    public void insertOnMap(SomeThing object) {
        int i = object.getY();
        int j = object.getX();

        if (i >= 0 && i < height && j >= 0 && j < width)
            map[i][j].setObject(object);
    }

    public void insertOnMap(SomeThing object, int x, int y) {
        if (y >= 0 && y < height && x >= 0 && x < width)
            map[y][x].setObject(object);
    }

    public Place getPlace(int x, int y) {
        if (y >= 0 && y < height && x >= 0 && x < width)
            return map[y][x];
        return OUT;
    }

    public void fillEnvironment(Place[][] env, Point center) {
        int height = env.length;
        int width = env[0].length;

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
}
