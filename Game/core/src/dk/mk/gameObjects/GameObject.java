package dk.mk.gameObjects;

import com.badlogic.gdx.graphics.Texture;

/** This class is the blueprint for renderable game objects. */
public abstract class GameObject {

    private Texture texture;

    public GameObject(String imagePath){
        this.texture = new Texture(imagePath);
    }

    public Texture getTexture(){
        return this.texture;
    }
}
