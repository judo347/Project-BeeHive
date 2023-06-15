package dk.mk.bee.game.objects.beespecies.template;

import dk.mk.bee.game.objects.GameObject;
import dk.mk.bee.game.objects.beespecies.template.type.HiveType;

import java.util.ArrayList;
import java.util.Arrays;

//TODO move to template as is?
public class Hive extends GameObject {

    private final HiveType type;

    private ArrayList<Bee> ownedBees;
    private ArrayList<Queen> queens;

    public Hive(HiveType type) {
        super(type.GAME_OBJECT_IDENTIFIER);
        this.type = type;

        this.ownedBees = new ArrayList<>();
        this.queens = new ArrayList<>();
    }

    @Override
    public void tick(float delta) {
        //TODO nothing to do, right?
    }

    public void addBees(Bee... bees) {
        this.ownedBees.addAll(Arrays.stream(bees).toList());
    }

    public void addQueen(Queen queen) {
        this.queens.add(queen);
    }

    public ArrayList<Bee> getOwnedBees() {
        return ownedBees;
    }

    public ArrayList<Queen> getQueens() {
        return queens;
    }
}
