package dk.mk.bee.game.objects.beespecies.template.type;

public enum HiveType {
    DEFAULT("hive_default_variant");
    public final String GAME_OBJECT_IDENTIFIER;

    HiveType(String gameObjectIdentifier) {
        GAME_OBJECT_IDENTIFIER = gameObjectIdentifier;
    }
}
