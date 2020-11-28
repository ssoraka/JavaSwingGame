package model;

import view.MyView;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TestModel implements ModelController, ModelView {
    final static public Point LEFT = new Point(-1, 0);
    final static public Point RIGHT = new Point(1, 0);
    final static public Point UP = new Point(0, -1);
    final static public Point DOWN = new Point(0, 1);

    final static private SomeThing EMPTY = new SomeThing(Types.EMPTY, -1, -1);
    final static private SomeThing BOUNDARY = new SomeThing(Types.BOUNDARY, -1, -1);

    final static private int WIDTH = 100;
    final static private int HEIGHT = 100;

    private List<String> messages;
    private boolean hasChange;

    private SomeThing map[][];
    private List<SomeThing> animals;
    private SomeThing player;
    Random random;

    public TestModel() {
        messages = new ArrayList<>();
        hasChange = true;

        random = new Random();
        animals = new ArrayList<>(100);
        map = new SomeThing[WIDTH][HEIGHT];
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                map[i][j] = EMPTY;
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
        player = new SomeThing(Types.PlAYER, 5, 5);
        insertOnMap(player);
    }

    private void insertOnMap(SomeThing object) {
        map[object.getY()][object.getX()] = object;
    }

    private void moveAnimals() {
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

    @Override
    public boolean hasChange() {
        return hasChange;
    }

    @Override
    public void applayChanges() {
        hasChange = false;
    }

    @Override
    public Point getPlayerPos() {
        return player.getPos();
    }

    @Override
    public List<String> getMessages() {
        return messages;
    }

    @Override
    public void fillEnvironment(SomeThing[][] env) {
        int height = env.length;
        int width = env[0].length;

        Point center = getPlayerPos();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int y = center.y + i - height / 2;
                int x = center.x + j - width / 2;
                if (y >= 0 && y < HEIGHT && x >= 0 && x < WIDTH)
                    env[i][j] = map[y][x];
                else
                    env[i][j] = BOUNDARY;
            }
        }
    }


    public void tryMoveObject(SomeThing object, Point shift) {
        Point pos = object.getPos();
        if (pos.x == 0 && shift.x == -1)
            return;
        if (pos.x == WIDTH - 1 && shift.x == 1)
            return;
        if (pos.y == 0 && shift.y == -1)
            return;
        if (pos.y == HEIGHT - 1 && shift.y == 1)
            return;
        if (map[pos.y + shift.y][pos.x + shift.x] != EMPTY)
            return;
        map[pos.y][pos.x] = EMPTY;
        pos.translate(shift.x, shift.y);
        insertOnMap(object);
    }

    // реакции на контроллеры

    @Override
    public void tryMovePlayer(Point shift) {
        tryMoveObject(player, shift);
        moveAnimals();
        hasChange = true;
    }

    @Override
    public void printMessage(String message) {
        messages.add(message);
        hasChange = true;
    }


    //надо отправлять какой-то интерфейс, чтоб вид мог себя обновить, пользуясь моделью
    @Override
    public String toString() {
        return "TestModel{" +
                ", messages=" + messages +
                '}';
    }
}
