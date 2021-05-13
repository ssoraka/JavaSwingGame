package model;

import model.war.Clazz;
import model.war.Player;
import model.war.Warrior;
import model.war.WarriorFabric;

import java.awt.*;

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
            player.heel();
        } else {
            level.tryMoveObject(player, shift);
            level.moveAnimals();
            if (!player.isAlive())
                throw new DeadException("Player is dead!!!");
        }
        hasChange = true;
    }

    @Override
    public boolean isMeetEnemy(Point shift) {
        return level.isMeetEnemy(player, shift);
    }

    @Override
    public void createNewPersonAndStartGame(String login, String password) {
        if (db.isLoginOrPasswordAlreadyExist(login, password))
            throw new RuntimeException("Такое имя или пароль уже существует");

        setPlayer(WarriorFabric.createPlayer(login, Clazz.CAPYBARA)); //подумать, игрок есть в классе Fighting
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
