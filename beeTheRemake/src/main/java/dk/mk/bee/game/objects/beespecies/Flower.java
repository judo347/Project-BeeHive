package dk.mk.bee.game.objects.beespecies;

import dk.mk.bee.game.objects.GameObject;
import dk.mk.bee.game.objects.beespecies.template.type.FlowerType;

public class Flower extends GameObject {

    private final FlowerType type;

    public Flower(FlowerType type) {
        super(type.GAME_OBJECT_IDENTIFIER);
        this.type = type;
    }
}
