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

    final static private PlaceHolder EMPTY = new PlaceHolder(Types.EMPTY);
    final static private PlaceHolder BOUNDARY = new PlaceHolder(Types.BOUNDARY);
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
        initMap();
    }

    public Level(Warrior player) {
        int size = getSize(player.getLevel());
        this.width = size;
        this.height = size;

        initMap();
        player.setXY(size / 2, size / 2);
        setPlayer(player);
    }

    private int getSize(int level) {
        return (level - 1) * 5 + 10 - (level % 2);
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

    private void initMap() {
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
                    animals.add(new Warrior("salamander", Types.ANIMAL, j, i));
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
            if (object != EMPTY && object != BOUNDARY) {
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



    public void tryMoveObject(Warrior object, Point shift)  {
        PlaceHolder current = getPlaceHolder(object.getX() + shift.x, object.getY() + shift.y);
        if (current == EMPTY) {
            Point pos = object.getPos();
            insertOnMap(EMPTY, pos.x, pos.y);
            pos.translate(shift.x, shift.y);
            insertOnMap(object);
        } else if (current instanceof Warrior) {
            fight(object, (Warrior) current);
        }
    }

    private void fight(Warrior warrior, Warrior enemy) {
        while (enemy.isAlive() && warrior.isAlive()) {
            enemy.takeDamage(warrior.attack());
            if (enemy.isAlive())
                warrior.takeDamage(enemy.attack());
        }
        insertOnMap(EMPTY, warrior.getX(), warrior.getY());
        if (warrior.isAlive()) {
            warrior.addExperience(enemy.getExperience());
            insertOnMap(warrior);
        } else {
            enemy.addExperience(warrior.getExperience());
        }
        getPlace(enemy.getX(), enemy.getY()).setType(Types.BLOOD);
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
