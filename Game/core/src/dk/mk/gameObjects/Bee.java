package dk.mk.gameObjects;

import com.badlogic.gdx.graphics.Texture;
import dk.mk.GameMapNew;
import dk.mk.Vector2;

public class Bee extends GameObject {

    private Texture pollenTexture;
    private Texture noPollenTexture;

    private boolean  hasPollen;
    private Vector2 hiveCoordinates;

    private GameMapNew.Direction currentHuntDirection;

    public static final int BEE_FLOWER_ALERT_DIST = 10;

    public Bee(int hiveX, int hiveY) {
        super("beeNoPollen.png");
        this.hiveCoordinates = new Vector2(hiveX, hiveY);

        this.pollenTexture = new Texture("beePollen.png");
        this.noPollenTexture = new Texture("beeNoPollen.png");

        this.hasPollen = false;

        this.currentHuntDirection = null;
    }

    public Vector2 getHiveCoordinates() {
        return new Vector2(hiveCoordinates);
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
