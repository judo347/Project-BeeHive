package dk.mk.gameObjects;

import com.badlogic.gdx.graphics.Texture;
import dk.mk.GameInfo;
import dk.mk.GameMap;
import dk.mk.Vector2;

public class Bee extends GameObject {

    private Texture pollenTexture;
    private Texture noPollenTexture;

    private boolean  hasPollen;
    private Vector2 hiveCoordinates;
    private GameMap.Direction currentHuntDirection;

    //Dictates the distance the bee can sense a flower. Measurement is in-game tiles.
    public static final int BEE_FLOWER_ALERT_DIST = GameInfo.BEE_FLOWER_ALERT_DIST;

    public Bee(int hiveX, int hiveY){
        this(new Vector2(hiveX, hiveY));
    }

    public Bee(Vector2 hiveCoords) {
        super("beeNoPollen.png");
        this.hiveCoordinates = hiveCoords;

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

    public GameMap.Direction getCurrentHuntDirection() {
        return currentHuntDirection;
    }

    public void setCurrentHuntDirection(GameMap.Direction currentHuntDirection) {
        this.currentHuntDirection = currentHuntDirection;
    }

    @Override
    public Texture getTexture() {
        return hasPollen ? pollenTexture : noPollenTexture;
    }
}
