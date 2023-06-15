package dk.mk.bee.game.objects.beespecies.template;

import dk.mk.bee.game.objects.beespecies.template.type.QueenType;

//TODO this is yet to be implemented. Would need type and other things.
public class Queen extends Creature {

    private final QueenType type;

    //TODO add hive which it belongs too
    /**
     * @param identifier
     * @param creatureLifespan may be null if the creature does not die after amount of time
     */
    public Queen(QueenType type, Float creatureLifespan) {
        super(type.GAME_OBJECT_IDENTIFIER, creatureLifespan);
        this.type = type;
    }

    //TODO overwrite tick and check for dead bees in hive and remove them
}
