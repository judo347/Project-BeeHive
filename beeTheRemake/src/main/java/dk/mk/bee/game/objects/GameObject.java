package dk.mk.bee.game.objects;

/** This class is the blueprint for render-able game objects. */
public abstract class GameObject {

    //TODO should be given an ENUM which describes texture for tile. In the visual part should translate it to image
      //TODO missing the parsing part
    private String identifier;

    public GameObject(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public abstract void tick(float delta);
}
