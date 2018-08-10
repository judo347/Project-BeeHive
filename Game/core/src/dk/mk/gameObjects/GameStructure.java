package dk.mk.gameObjects;

public class GameStructure extends GameObject {

    private boolean isSolid;

    public GameStructure(boolean isSolid) {
        super(isSolid ? "border.png" : "blank.png");
        this.isSolid = isSolid;
    }

    public boolean isSolid() {
        return isSolid;
    }
}
