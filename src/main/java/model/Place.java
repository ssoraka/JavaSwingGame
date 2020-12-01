package model;

public class Place {
    private Warrior object;
    private Types type;
    //тут будут эффекты местности и ее внешний вид...


    public Place(Warrior object) {
        this.object = object;
        this.type = Types.GREEN;
    }

    public Place(Warrior object, Types type) {
        this.object = object;
        this.type = type;
    }

    public Warrior getObject() {
        return object;
    }

    public void setObject(Warrior object) {
        this.object = object;
    }

    public Types getType() {
        return type;
    }

    public void setType(Types type) {
        this.type = type;
    }
}
