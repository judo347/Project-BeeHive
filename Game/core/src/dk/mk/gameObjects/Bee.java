package dk.mk.gameObjects;

import com.badlogic.gdx.graphics.Texture;

public class Bee extends GameObject {

    private Texture pollenTexture;
    private Texture noPollenTexture;

    private boolean  hasPollen;
    private int hiveX;
    private int hiveY;

    public Bee(int hiveX, int hiveY) {
        super("beeNoPollen.png");
        this.hiveX = hiveX;
        this.hiveY = hiveY;

        this.pollenTexture = new Texture("beePollen.png");
        this.noPollenTexture = new Texture("beeNoPollen.png");

        this.hasPollen = false;
    }

    public int getHiveX() {
        return hiveX;
    }

    public int getHiveY() {
        return hiveY;
    }

    public boolean hasPollen(){
        return this.hasPollen;
    }

    public void givePollen(){
        this.hasPollen = true;
    }

    public void removePollen(){
        this.hasPollen = false;
    }

    @Override
    public Texture getTexture() {
        return hasPollen ? pollenTexture : noPollenTexture;
    }
}
