package dk.mk.bee.game.objects.tile;

public enum TileType {
    BORDER("tile_border", true), WHITESPACE("tile_whitespace", false);

    public final boolean IS_SOLID;
    public final String GAME_OBJECT_IDENTIFIER;

    TileType(String gameObjectIdentifier, boolean is_solid) {
        GAME_OBJECT_IDENTIFIER = gameObjectIdentifier;
        IS_SOLID = is_solid;
    }
}
