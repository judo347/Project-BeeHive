package dk.mk.bee.game.objects.beespecies.template.type;

public enum FlowerType {
    DEFAULT("flower_default_variant");
    public final String GAME_OBJECT_IDENTIFIER;

    FlowerType(String gameObjectIdentifier) {
        GAME_OBJECT_IDENTIFIER = gameObjectIdentifier;
    }
}
