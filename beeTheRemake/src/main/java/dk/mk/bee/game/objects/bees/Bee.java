package dk.mk.bee.game.objects.bees;

import dk.mk.bee.game.config.GameInfo;
import dk.mk.bee.game.map.Direction;
import dk.mk.bee.game.objects.GameObject;
import dk.mk.bee.game.util.Vector2;


public class Bee extends GameObject {
    private final BeeType type;
    private float lifetime;
    private boolean isDead;

    private boolean  hasPollen;
    private Vector2 hiveCoordinates; //The hive the bee belongs too - home
    private Direction currentHuntDirection;

    //Dictates the distance the bee can sense a flower. Measurement is in-game tiles.
    public static final int BEE_FLOWER_ALERT_DIST = GameInfo.BEE_FLOWER_ALERT_DIST();

    public Bee(int hiveX, int hiveY, BeeType type){
        this(new Vector2(hiveX, hiveY), type);
    }

    public Bee(Vector2 hiveCoordinates, BeeType type) {
        super(type.GAME_OBJECT_IDENTIFIER);
        this.type = type;
        this.hiveCoordinates = hiveCoordinates;

        this.hasPollen = false;

        this.currentHuntDirection = null;

        this.lifetime = 0;
        this.isDead = false;
    }

    /** Used to update the counter for how long this bee has lived.
     * Should be called each game tick. Also flags the bee as dead if time exceeds allowed lifespan. */
    public void updateLifetime(float delta){
        //System.out.println(lifetime);
        lifetime += delta;
        //System.out.println(this.toString() + " " + lifetime + " " + delta);
        if(lifetime >= GameInfo.BEE_LIFETIME())
            this.isDead = true;
    }

    public Vector2 getHiveCoordinates() {
        return new Vector2(hiveCoordinates.x(), hiveCoordinates.y());
    }

    public boolean hasPollen(){
        return this.hasPollen;
    }

    public void givePollen(){
        this.hasPollen = true;
    }

    public void removePollen(){
        this.hasPollen = false;
        this.currentHuntDirection = null; //Reset direction when pollen is harvested = it found home/hive
    }

    public Direction getCurrentHuntDirection() {
        return currentHuntDirection;
    }

    public void setCurrentHuntDirection(Direction currentHuntDirection) {
        this.currentHuntDirection = currentHuntDirection;
    }

    /* //TODO should not be here. Should be calculated in visual part based on boolean values in this class
    @Override
    public Texture getTexture() {
        return hasPollen ? pollenTexture : noPollenTexture;
    } */

    public boolean isDead() {
        return isDead;
    }
}
