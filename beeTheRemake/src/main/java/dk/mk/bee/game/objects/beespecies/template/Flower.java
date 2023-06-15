package dk.mk.bee.game.objects.beespecies.template;

import dk.mk.bee.game.objects.GameObject;
import dk.mk.bee.game.objects.beespecies.template.type.FlowerType;

//TODO should be moved to template folder
public class Flower extends GameObject {

    private final FlowerType type;

    public Flower(FlowerType type) {
        super(type.GAME_OBJECT_IDENTIFIER);
        this.type = type;
    }

    @Override
    public void tick(float delta) {
        //TODO do nothing? Or maybe add a bloom feature? Blooms after some time, only then is pollen available, and then dies after some time?
    }
}
