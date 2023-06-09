package dk.mk.bee.game.objects.bees;

public enum BeeType {
    DEFAULT("bee_default_variant");
    public final String GAME_OBJECT_IDENTIFIER;

    BeeType(String gameObjectIdentifier) {
        GAME_OBJECT_IDENTIFIER = gameObjectIdentifier;
    }
}
