package model;

import model.war.PlaceHolder;

public class Place {
    private PlaceHolder object;
    private Types type;
    //тут будут эффекты местности и ее внешний вид...


    public Place(PlaceHolder object) {
        this.object = object;
        this.type = Types.GREEN;
    }

    public Place(PlaceHolder object, Types type) {
        this.object = object;
        this.type = type;
    }

    public PlaceHolder getObject() {
        return object;
    }

    public void setObject(PlaceHolder object) {
        this.object = object;
    }

    public Types getType() {
        return type;
    }

    public void setType(Types type) {
        this.type = type;
    }
}
