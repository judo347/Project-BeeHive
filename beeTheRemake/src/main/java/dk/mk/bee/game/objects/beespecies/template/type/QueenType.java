package dk.mk.bee.game.objects.beespecies.template.type;

public enum QueenType {
    DEFAULT("queen_default_variant");
    public final String GAME_OBJECT_IDENTIFIER;

    QueenType(String gameObjectIdentifier) {
        GAME_OBJECT_IDENTIFIER = gameObjectIdentifier;
    }
}