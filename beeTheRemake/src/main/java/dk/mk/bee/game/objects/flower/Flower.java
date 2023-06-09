package dk.mk.bee.game.objects.flower;

import dk.mk.bee.game.objects.GameObject;

public class Flower extends GameObject {

    private final FlowerType type;

    public Flower(FlowerType type) {
        super(type.GAME_OBJECT_IDENTIFIER);
        this.type = type;
    }
}
