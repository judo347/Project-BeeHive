package dk.mk.bee.ui.config;

public class VisualInfo {
    private static final int WINDOW_WIDTH = 1000; //Default = 1000
    private static final int WINDOW_HEIGHT = 800; //Default = 800
    private static final int SQUARE_SIZE = 22; //Size of the game squares //Default = 10

    public static final int FOREGROUND_FPS = 60; //Default = 60
    public static final int TICK_INTERVAL = 16; //Default = 16
    public static final String TITLE = "Project: BeeHive 2";

    public static int WINDOW_WIDTH() {
        return WINDOW_WIDTH;
    }

    public static int WINDOW_HEIGHT() {
        return WINDOW_HEIGHT;
    }

    public static int SQUARE_SIZE() {
        return SQUARE_SIZE;
    }
}
