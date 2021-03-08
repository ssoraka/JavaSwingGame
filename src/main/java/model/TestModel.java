package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TestModel implements ModelController, ModelView {
    private DAO db;
    private boolean hasChange;

    private Level level;
    private Warrior player;

    public TestModel() {
        hasChange = true;
    }

    public void setDb(DAO db) {
        this.db = db;
    }

    public boolean wasChanged() {
        if (hasChange) {
            hasChange = false;
            return true;
        }
        return false;
    }

    @Override
    public void fillEnvironment(Place[][] env) {
        level.fillEnvironment(env);
    }

    @Override
    public Warrior getPlayer() {
        return player;
    }

    private void setPlayer(Warrior warrior) {
        player = warrior;
        level = new Level(player);
    }

    // реакции на контроллеры

    @Override
    public void tryMovePlayer(Point shift) {
        if (level.isLeaveLevel(player, shift)) {
            db.updatePlayer(player);
            level = new Level(player);
        } else {
            level.tryMoveObject(player, shift);
            level.moveAnimals();
            if (!player.isAlive())
                throw new DeadException();
        }
        hasChange = true;
    }

    @Override
    public void createNewPersonAndStartGame(String login, String password) {
        if (db.isLoginOrPasswordAlreadyExist(login, password))
            throw new RuntimeException("Такое имя или пароль уже существует");

        setPlayer(new Warrior(login, Types.PlAYER));
        db.createPlayer(login, password, player);
    }

    @Override
    public void findPersonAndStartGame(String login, String password) {
        if (!db.isLoginAndPasswordAlreadyExist(login, password))
            throw new RuntimeException("Неверное имя или пароль");
        setPlayer(db.readPlayer(login, password));
    }

    @Override
    public void exit() {
        db.closeDB();
        System.exit(0);
    }
}
