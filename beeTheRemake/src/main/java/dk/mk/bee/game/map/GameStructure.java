package dk.mk.bee.game.map;

import dk.mk.bee.game.objects.tile.TileType;
import dk.mk.bee.game.objects.GameObject;

//TODO should be replaced with Tile
@Deprecated
public class GameStructure extends GameObject {

    private final boolean isSolid; //Used to determine border or empty space

    public GameStructure(boolean isSolid) {
        super(isSolid ? TileType.BORDER.GAME_OBJECT_IDENTIFIER : TileType.WHITESPACE.GAME_OBJECT_IDENTIFIER);
        this.isSolid = isSolid;
    }

    public boolean isSolid() {
        return isSolid;
    }
}
