package dk.mk.bee.game.objects.hive;

import dk.mk.bee.game.objects.GameObject;
import dk.mk.bee.game.objects.bees.Bee;

import java.util.ArrayList;

public class Hive extends GameObject {

    private final HiveType type;

    ArrayList<Bee> ownedBees;

    public Hive(HiveType type) {
        super(type.GAME_OBJECT_IDENTIFIER);
        this.type = type;

        ownedBees = new ArrayList<Bee>();
    }
}
