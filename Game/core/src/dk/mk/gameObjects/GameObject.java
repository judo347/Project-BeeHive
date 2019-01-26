package dk.mk.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/** This class is the blueprint for renderable game objects. */
public abstract class GameObject {

    private Texture texture;

    public GameObject(String imagePath){
        try{
            this.texture = new Texture (imagePath);
        }catch (NullPointerException e){
            System.out.println("TEXTURE COULD NOT BE LOADED: " + imagePath);
        }
    }

    public Texture getTexture(){
        return this.texture;
    }
}
