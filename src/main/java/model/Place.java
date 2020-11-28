package model;

public class Place {
    private SomeThing object;
    //тут будут эффекты местности и ее внешний вид...


    public Place(SomeThing object) {
        this.object = object;
    }

    public SomeThing getObject() {
        return object;
    }

    public void setObject(SomeThing object) {
        this.object = object;
    }


}
