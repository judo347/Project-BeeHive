package dk.mk.bee.game.config;

public class GameInfo {
    private static final int GAME_SQUARE_COUNT_HORIZONTAL = 40; //Size of the game squares //Default = 10
    private static final int GAME_SQUARE_COUNT_VERTICAL = 30; //Size of the game squares //Default = 10
    private static final float TICK_DURATION = 0.2f; //Duration of each game tick. Lower = faster //Default = 1f (Has to be float)
    private static final int BEE_FLOWER_ALERT_DIST = 10;
    private static final float BEE_LIFETIME = 1f; //The max lifetime for a bee

    public static int GAME_SQUARE_COUNT_HORIZONTAL() {
        return GAME_SQUARE_COUNT_HORIZONTAL;
    }

    public static int GAME_SQUARE_COUNT_VERTICAL() {
        return GAME_SQUARE_COUNT_VERTICAL;
    }

    public static float TICK_DURATION() {
        return TICK_DURATION;
    }

    public static int BEE_FLOWER_ALERT_DIST() {
        return BEE_FLOWER_ALERT_DIST;
    }

    public static float BEE_LIFETIME() {
        return BEE_LIFETIME;
    }
}
