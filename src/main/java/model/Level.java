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

    final static private Warrior EMPTY = new Warrior("empty", Types.EMPTY, -1, -1);
    final static private Warrior BOUNDARY = new Warrior("boundary", Types.BOUNDARY, -1, -1);
    final static private Place OUT = new Place(BOUNDARY, Types.BLACK);


    private int width;
    private int height;
    private Place[][] map;
    private List<Warrior> animals;
    private Warrior player;

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

    public Warrior getPlayer() {
        return player;
    }

    public void setPlayer(Warrior player) {
        this.player = player;
        insertOnMap(player);

 /*       animals.add(new Warrior("salamander", Types.ANIMAL, 4, 4));
        insertOnMap(animals.get(animals.size() - 1));
        animals.add(new Warrior("salamander", Types.ANIMAL, 5, 4));
        insertOnMap(animals.get(animals.size() - 1));

        tryMoveObject(animals.get(animals.size() - 1), new Point(-1, 0));

  */
    }

    public void initMapByRandomValue() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int tmp = random.nextInt(100);
                if (tmp < 1) {
                    animals.add(new Warrior("salamander", Types.ANIMAL, j, i));
                    insertOnMap(animals.get(animals.size() - 1));
                } else if (tmp < 7) {
                    insertOnMap(new Warrior("stone", Types.STONE, j, i));
                } else if (tmp < 10) {
                    insertOnMap(new Warrior("tree", Types.TREE, j, i));
                }
            }
        }
    }

    private void insertOnMap(Warrior object, int x, int y) {
        if (y >= 0 && y < height && x >= 0 && x < width)
            map[y][x].setObject(object);
    }

    public void insertOnMap(Warrior object) {
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

    public Warrior isGetWarrior(int x, int y) {
        if (y < 0 || y >= height || x < 0 || x >= width)
            return BOUNDARY;
        return map[y][x].getObject();
    }

    public void tryMoveObject(Warrior object, Point shift)  {
        Warrior current = isGetWarrior(object.getX() + shift.x, object.getY() + shift.y);
        if (current == EMPTY) {
            Point pos = object.getPos();
            insertOnMap(EMPTY, pos.x, pos.y);
            pos.translate(shift.x, shift.y);
            insertOnMap(object);
        } else if (current.getTypes() == Types.ANIMAL || current.getTypes() == Types.PlAYER) {
            fight(object, current);
        }
    }

    public void fight(Warrior warrior, Warrior enemy) {
        while (enemy.isAlive() && warrior.isAlive()) {
            enemy.takeDamage(warrior.attack());
            if (enemy.isAlive())
                warrior.takeDamage(enemy.attack());
        }
        if (warrior.isAlive()) {
            warrior.addExperience(enemy.getExperience());
            animals.remove(enemy);
            insertOnMap(EMPTY, enemy.getX(), enemy.getY());
        } else {
            enemy.addExperience(warrior.getExperience());
            insertOnMap(EMPTY, warrior.getX(), warrior.getY());
        }
    }

    public void moveAnimals() {
        for (Warrior animal : animals) {
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
