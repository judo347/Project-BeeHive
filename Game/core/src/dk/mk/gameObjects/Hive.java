package dk.mk.gameObjects;

import java.util.ArrayList;

public class Hive extends GameObject {

    ArrayList<Bee> ownedBees;

    public Hive() {
        super("hive.png");

        ownedBees = new ArrayList<Bee>();
    }
}
