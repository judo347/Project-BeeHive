package dk.mk.bee.game.objects.tile;

import dk.mk.bee.game.objects.GameObject;

public class Tile extends GameObject {
    private final TileType type;

    public Tile(TileType type) {
        super(type.GAME_OBJECT_IDENTIFIER);
        this.type = type;
    }

    public TileType getType() {
        return type;
    }

    public boolean isSolid() {
        return type.IS_SOLID;
    }
}
