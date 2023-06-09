package dk.mk.bee.game.map;

//import com.badlogic.gdx.graphics.Texture;

import dk.mk.bee.TileType;

/** This class is the blueprint for renderable game objects. */
public abstract class GameObject {

    //TODO should be given an ENUM which describes texture for tile. In the visual part should translate it to image
    private TileType tileType;

    public GameObject(TileType tileType) {
        this.tileType = tileType;
    }

    public TileType getType() {
        return tileType;
    }
}
