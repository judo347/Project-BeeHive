package dk.mk.gameObjects;

public class Bee extends GameObject {

    private boolean  hasPollen;
    //private int x;
    //private int y;

    public Bee() {
        super("bee.png");
        //this.x = x;
        //this.y = y;

        this.hasPollen = false;
    }

    /*
    public void setCoords(int x, int y) {
        this.x = x;
        this.y = y;
    }*/

    public void hasPollen(boolean hasPollen){
        this.hasPollen = hasPollen;
    }
}
