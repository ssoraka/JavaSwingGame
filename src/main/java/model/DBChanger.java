package model;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class DBChanger {

    List<Pair<String, String>> db;

    public DBChanger() {
        db = new ArrayList<>();
        db.add(new Pair<>("1", "1"));
        db.add(new Pair<>("2", "2"));
        db.add(new Pair<>("3", "3"));
    }

    public boolean isLoginAndPasswordAlreadyExist(String login, String password) {
        for (Pair<String, String> pair :
                db) {
            if (pair.getKey().equals(login) || pair.getValue().equals(password))
                return true;
        }
        return false;
    }

    public void addNewPlayer(String login, String password) {
        db.add(new Pair<>(login, password));
    }
}
