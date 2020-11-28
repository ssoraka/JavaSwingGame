package model;

public class Place {
    private SomeThing object;
    private Types type;
    //тут будут эффекты местности и ее внешний вид...


    public Place(SomeThing object) {
        this.object = object;
        this.type = Types.GREEN;
    }

    public Place(SomeThing object, Types type) {
        this.object = object;
        this.type = type;
    }

    public SomeThing getObject() {
        return object;
    }

    public void setObject(SomeThing object) {
        this.object = object;
    }

    public Types getType() {
        return type;
    }

    public void setType(Types type) {
        this.type = type;
    }
}
