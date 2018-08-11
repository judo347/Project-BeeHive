package dk.mk.gameObjects;

import com.badlogic.gdx.graphics.Texture;
import dk.mk.GameMapNew;

public class Bee extends GameObject {

    private Texture pollenTexture;
    private Texture noPollenTexture;

    private boolean  hasPollen;
    private int hiveX;
    private int hiveY;

    private GameMapNew.Direction currentHuntDirection;

    public static final int BEE_FLOWER_ALERT_DIST = 10;

    public Bee(int hiveX, int hiveY) {
        super("beeNoPollen.png");
        this.hiveX = hiveX;
        this.hiveY = hiveY;

        this.pollenTexture = new Texture("beePollen.png");
        this.noPollenTexture = new Texture("beeNoPollen.png");

        this.hasPollen = false;

        this.currentHuntDirection = null;
    }

    public int getHiveX() {
        return hiveX;
    }

    public int getHiveY() {
        return hiveY;
    }

    public boolean hasPollen(){
        return this.hasPollen;
    }

    public void givePollen(){
        this.hasPollen = true;
    }

    public void removePollen(){
        this.hasPollen = false;
        this.currentHuntDirection = null; //Reset direction when pollen is harvested = it found home
    }

    public GameMapNew.Direction getCurrentHuntDirection() {
        return currentHuntDirection;
    }

    public void setCurrentHuntDirection(GameMapNew.Direction currentHuntDirection) {
        this.currentHuntDirection = currentHuntDirection;
    }

    @Override
    public Texture getTexture() {
        return hasPollen ? pollenTexture : noPollenTexture;
    }
}
