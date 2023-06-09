package dk.mk.bee.game.exception;

public class IllegalGameStart extends RuntimeException {

    public IllegalGameStart(int beeCount, int hiveCount) {
        super("Game could not start! Insufficient bees or/and hives" + "\n" + "Bees: " + beeCount + " Hives: " + hiveCount);
    }
}
