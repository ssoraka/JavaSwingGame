package model;

import model.war.*;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Level {
    final static public Point LEFT = new Point(-1, 0);
    final static public Point RIGHT = new Point(1, 0);
    final static public Point UP = new Point(0, -1);
    final static public Point DOWN = new Point(0, 1);
    final static public Point HERE = new Point(0, 0);

    private int width;
    private int height;
    private int level;

    private Place[][] map;
    private LinkedHashMap<Warrior, Point> enemy;
    private Warrior player;
    private Point heroPos;

    private Random random;

    public Level(int width, int height) {
        this.width = width;
        this.height = height;
        level = 1;
    }

    public Level(Warrior player) {
        level = player.getLevel();
        int size = getSize(level);
        width = size;
        height = size;
        map = new Place[height][width];
        enemy = new LinkedHashMap<>();
        random = new Random();

        initMap();

        heroPos = new Point(size / 2, size / 2);
        this.player = player;
        map[size / 2][size / 2].setWarrior(player);
    }

    private int getSize(int level) {
        return (level - 1) * 5 + 10 - (level % 2);
    }

    public void initMap() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (y == height / 2 || x == width / 2) {
                    map[y][x] = new Place();
                    continue;
                }

                Place place = new Place();
                int rnd = random.nextInt(100);
                if (rnd < 4) {
                    place.setWarrior(WarriorFabric.randomWarrior(level));
                    enemy.put(place.getWarrior(), new Point(x, y));
                } else if (rnd < 7) {
                    place.setType(Types.STONE);
                } else if (rnd < 10) {
                    place.setType(Types.TREE);
                }
                map[y][x] = place;
            }
        }
    }

    private Place getPlace(int x, int y) {
        if (y >= 0 && y < height && x >= 0 && x < width)
            return map[y][x];
        return Place.OUT;
    }

    public void fillEnvironment(Place[][] env) {
        int height = env.length;
        int width = env[0].length;
        Point center = heroPos;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int y = center.y + i - height / 2;
                int x = center.x + j - width / 2;

                env[i][j] = getPlace(x, y);
            }
        }
    }

    public boolean isHeroLeaveLevel(Point shift) {
        return getPlace(heroPos.x + shift.x, heroPos.y + shift.y) == Place.OUT;
    }

    public boolean isHeroMeetEnemy(Point shift) {
        return getPlace(heroPos.x + shift.x, heroPos.y + shift.y).hasWarrior();
    }

    private void move(Warrior warrior, Point pos, Point shift) {
        if (shift.equals(HERE) || !warrior.isAlive()) {
            return ;
        }

        Place current = getPlace(pos.x + shift.x, pos.y + shift.y);
        if (current.isEmpty()) {
            getPlace(pos.x, pos.y).free();
            pos.translate(shift.x, shift.y);
            current.setWarrior(warrior);
        } else if (current.hasWarrior()) {
            Fighter enemy = current.getWarrior();
            getPlace(pos.x, pos.y).free();
            pos.translate(shift.x, shift.y);
            current.setType(Types.BLOOD);

            if (Fighting.fight(warrior, enemy).equals(warrior)) {
                current.setWarrior(warrior);
            }
        }
    }

    public void moveHero(Point shift) {
        move(player, heroPos, shift);
    }

    public void moveAnimals() {
        List<Warrior> warriors = new ArrayList<>(enemy.keySet());
        for (Warrior warrior : warriors) {
            if (!enemy.containsKey(warrior)) {
                continue;
            }
            Point pos = enemy.get(warrior);

            switch (random.nextInt(5)) {
                case 0 : move(warrior, pos, UP); break;
                case 1 : move(warrior, pos, DOWN); break;
                case 2 : move(warrior, pos, LEFT); break;
                case 3 : move(warrior, pos, RIGHT); break;
                default:
                    break;
            }

            if (!warrior.isAlive()) {
                enemy.remove(warrior);
            }
        }
    }
}
