package model;

import model.items.Item;
import model.war.Clazz;
import model.war.Fighting;
import model.war.Warrior;
import model.war.WarriorFabric;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MyModel implements ModelController, ModelView {
    private DAO db;

    private Level level;
    private Warrior player;
    private List<Item> items;

    public MyModel() {
        items = Fighting.getItems();
    }

    public void setDb(DAO db) {
        this.db = db;
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
        Fighting.setPlayer(player);
    }

    @Override
    public boolean hasItems() {
        return !items.isEmpty();
    }

    public Item getItem() {
        return items.remove(0);
    }
    // реакции на контроллеры

    @Override
    public void movePlayer(Point shift) {
        if (level.isHeroLeaveLevel(shift)) {
            db.updatePlayer(player);
            level = new Level(player);
            player.heel();
        } else {
            level.moveHero(shift);
            if (!player.isAlive())
                throw new DeadException("Player is dead!!!");
        }
    }

    @Override
    public void moveWorld() {
        level.moveAnimals();
        if (!player.isAlive())
            throw new DeadException("Player is dead!!!");
    }

    @Override
    public boolean isMeetEnemy(Point shift) {
        return level.isHeroMeetEnemy(shift);
    }

    @Override
    public void createNewPersonAndStartGame(String login, String password, Clazz clazz) {
        if (db.isLoginAlreadyExist(login))
            throw new DAOException("Такое имя уже существует");

        setPlayer(WarriorFabric.createPlayer(login, clazz));
        db.createPlayer(login, password, player);
    }

    @Override
    public void findPersonAndStartGame(String login, String password) {
        if (!db.isLoginAndPasswordAlreadyExist(login, password))
            throw new DAOException("Неверное имя или пароль");
        setPlayer(db.readPlayer(login, password));
    }

    @Override
    public void exit() {
        db.closeDB();
        System.exit(0);
    }
}
