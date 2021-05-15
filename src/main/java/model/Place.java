package model;

import model.war.Warrior;

public class Place {
    final static public Place OUT = new Place(Types.BOUNDARY);

    private Types type;

    private boolean isEmpty;
    private Warrior warrior;
    //тут будут эффекты местности и ее внешний вид...

    public Place() {
        type = Types.GREEN;
        isEmpty = true;
    }

    public Place(Types type) {
        isEmpty = false;
        this.type = type;
    }

    public Types getType() {
        return type;
    }

    public void setType(Types type) {
        this.type = type;
        isEmpty = (type != Types.STONE && type != Types.TREE && type != Types.BLACK);
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public Warrior getWarrior() {
        return warrior;
    }

    public void setWarrior(Warrior warrior) {
        this.warrior = warrior;
        isEmpty = warrior == null;
    }

    public boolean hasWarrior() {
        return warrior != null;
    }

    public void free() {
        setWarrior(null);
    }
}
