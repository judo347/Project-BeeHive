package dk.mk.bee.game.objects.beespecies.template;

import dk.mk.bee.game.config.GameInfo;
import dk.mk.bee.game.objects.GameObject;
import dk.mk.bee.game.objects.beespecies.template.type.BeeType;
import dk.mk.bee.game.util.Vector2;

public class Bee extends Creature {
    private final BeeType type;
    private boolean hasPollen;
    private GameObject home; //Will most likely be a hive
    private Vector2 homeLocation; //Will most likely be a hive

    //Dictates the distance the bee can sense a flower. Measurement is in-game tiles.
    public static final int FLOWER_ALERT_DIST = GameInfo.BEE_FLOWER_ALERT_DIST();


    public Bee(BeeType type, Float creatureLifespan, GameObject home, Vector2 homeLocation) {
        super(type.GAME_OBJECT_IDENTIFIER, creatureLifespan);
        this.type = type;
        updateHome(home, homeLocation);
    }

    public void updateHome(GameObject home, Vector2 homeLocation) {
        this.home = home;
        this.homeLocation = homeLocation;
    }

    public void removePollen(){
        this.hasPollen = false;
        this.setMovementDirection(null); //Reset direction when pollen is harvested = it found home/hive
    }

    /* //TODO should not be here. Should be calculated in visual part based on boolean values in this class
    @Override
    public Texture getTexture() {
        return hasPollen ? pollenTexture : noPollenTexture;
    } */
}
