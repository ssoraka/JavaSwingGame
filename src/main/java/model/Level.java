package model;

import model.war.Clazz;
import model.war.PlaceHolder;
import model.war.Warrior;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Level {
    final static public Point LEFT = new Point(-1, 0);
    final static public Point RIGHT = new Point(1, 0);
    final static public Point UP = new Point(0, -1);
    final static public Point DOWN = new Point(0, 1);
    final static public Point HERE = new Point(0, 0);

    final static private PlaceHolder EMPTY = new PlaceHolder(Types.EMPTY);
    final static private PlaceHolder BOUNDARY = new PlaceHolder(Types.BOUNDARY);
    final static private Place OUT = new Place(BOUNDARY, Types.BLACK);


    private int width;
    private int height;
    private int level;

    private Place[][] map;
    private List<Warrior> animals;
    private Warrior player;

    private Random random;

    private StringBuilder logger;
    private boolean loggerOn;

    // надо сделать чтение карты с файла
    public Level(int width, int height) {
        this.width = width;
        this.height = height;
        level = 1;
    }

    public Level(Warrior player) {
        level = player.getLevel();
        int size = getSize(level);
        this.width = size;
        this.height = size;

        initMap();
        player.setXY(size / 2, size / 2);
        setPlayer(player);

        logger = new StringBuilder("");
    }

    private int getSize(int level) {
        return (level - 1) * 5 + 10 - (level % 2);
    }

    public void setPlayer(Warrior player) {
        this.player = player;
        insertOnMap(player);
    }

    public void initMap() {
        random = new Random();
        animals = new ArrayList<>();
        map = new Place[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                map[i][j] = new Place(EMPTY);
                if (i == height / 2 && j == width / 2)
                    continue;
                int tmp = random.nextInt(100);
                if (tmp < 4) {
                    animals.add(new Warrior("Salamander", Clazz.ANIMAL, j, i, level));
                    insertOnMap(animals.get(animals.size() - 1));
                } else if (tmp < 7) {
                    insertOnMap(new PlaceHolder(Types.STONE, j, i));
                } else if (tmp < 10) {
                    insertOnMap(new PlaceHolder(Types.TREE, j, i));
                }
            }
        }
    }

    private void insertOnMap(PlaceHolder object, int x, int y) {
        if (y >= 0 && y < height && x >= 0 && x < width) {
            map[y][x].setObject(object);
            if (object != EMPTY) {
                object.setXY(x, y);
            }
        }
    }

    private void insertOnMap(PlaceHolder object) {
        int x = object.getX();
        int y = object.getY();
        insertOnMap(object, x, y);
    }

    private Place getPlace(int x, int y) {
        if (y >= 0 && y < height && x >= 0 && x < width)
            return map[y][x];
        return OUT;
    }

    private PlaceHolder getPlaceHolder(int x, int y) {
        return  getPlace(x, y).getObject();
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

    public boolean isLeaveLevel(Warrior object, Point shift) {
        return getPlaceHolder(object.getX() + shift.x, object.getY() + shift.y) == BOUNDARY;
    }

    public void tryMoveObject(Warrior warrior, Point shift) {
        if (shift.equals(HERE)) {
            return ;
        }

        PlaceHolder current = getPlaceHolder(warrior.getX() + shift.x, warrior.getY() + shift.y);
        if (current == EMPTY) {
            Point pos = warrior.getPos();
            insertOnMap(EMPTY, pos.x, pos.y);
            pos.translate(shift.x, shift.y);
            insertOnMap(warrior);
        } else if (current instanceof Warrior) {
            Warrior enemy = (Warrior) current;
            insertOnMap(EMPTY, warrior.getX(), warrior.getY());
            Warrior winner = warrior.fight(enemy);
            insertOnMap(winner, enemy.getX(), enemy.getY());
            getPlace(winner.getX(), winner.getY()).setType(Types.BLOOD);
        }
    }

    public boolean isMeetEnemy(Warrior warrior, Point shift) {
        PlaceHolder current = getPlaceHolder(warrior.getX() + shift.x, warrior.getY() + shift.y);
        return (current instanceof Warrior);
    }

    public void moveAnimals() {
        for (Warrior animal : animals) {
            if (!animal.isAlive())
                continue;
            switch (random.nextInt(5)) {
                case 0 : tryMoveObject(animal, UP); break;
                case 1 : tryMoveObject(animal, DOWN); break;
                case 2 : tryMoveObject(animal, LEFT); break;
                case 3 : tryMoveObject(animal, RIGHT); break;
                default:
                    break;
            }
        }
        for (int i = animals.size(); i > 0 ; i--) {
            if (!animals.get(i - 1).isAlive())
                animals.remove(i - 1);
        }
    }
}
