package dk.mk.bee.game.map;

import dk.mk.bee.TileType;

public class GameStructure extends GameObject {

    private final boolean isSolid; //Used to determine border or empty space

    public GameStructure(boolean isSolid) {
        super(isSolid ? TileType.BORDER : TileType.WHITE_SPACE);
        this.isSolid = isSolid;
    }

    public boolean isSolid() {
        return isSolid;
    }
}
