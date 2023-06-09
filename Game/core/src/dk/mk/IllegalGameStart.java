package dk.mk;

//+
public class IllegalGameStart extends RuntimeException {

    public IllegalGameStart(int beeCount, int hiveCount) {
        System.out.println("Game could not start! Insufficient bees or/and hives");
        System.out.println("Bees: " + beeCount + " Hives: " + hiveCount);
    }
}
