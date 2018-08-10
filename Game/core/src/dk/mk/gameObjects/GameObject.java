package dk.mk.gameObjects;

import com.badlogic.gdx.graphics.Texture;

public abstract class GameObject {

    private Texture texture;

    public GameObject(String imagePath){
        this.texture = new Texture(imagePath);
    }

    public Texture getTexture(){
        return this.texture;
    }
}
