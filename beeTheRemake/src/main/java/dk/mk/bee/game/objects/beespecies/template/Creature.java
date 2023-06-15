package dk.mk.bee.game.objects.beespecies.template;

import dk.mk.bee.game.map.Direction;
import dk.mk.bee.game.objects.GameObject;

public class Creature extends GameObject {
    private Float remainingLifespan; //In seconds how long the bee has left to live
    private final Float lifespan; //In seconds how long in total the bee can live
    private boolean isDead;
    private Direction movementDirection;

    /**
     * @param creatureLifespan may be null if the creature does not die after amount of time */
    public Creature(String identifier, Float creatureLifespan) {
        super(identifier);
        this.lifespan = creatureLifespan;
        this.remainingLifespan = creatureLifespan;
        this.isDead = false;
        this.movementDirection = null;
    }

    public float getRemainingLifespan() {
        return remainingLifespan;
    }

    public float getLifespan() {
        return lifespan;
    }

    public boolean isDead() {
        return isDead;
    }

    /** Used to update the counter for how long the creature has left to live. (decrease)
     * Should be called each game tick. Also flags the creature as dead if time lifetime runs out. */
    public void updateLifespan(float change) {
        if (remainingLifespan != null) {
            remainingLifespan -= change;
            //System.out.println(this.toString() + " " + lifetime + " " + delta);
            if(remainingLifespan <= 0) {
                this.isDead = true;
            }
        }
    }

    public Direction getMovementDirection() {
        return movementDirection;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public void setMovementDirection(Direction movementDirection) {
        this.movementDirection = movementDirection;
    }

    @Override
    public void tick(float delta) {
        updateLifespan(delta);
        //TODO call move logic
        //TODO call change state logic? Not here, but how to make it animated?
    }
}
