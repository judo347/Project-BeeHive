package dk.mk.bee.game.map;

import dk.mk.bee.config.Logger;
import dk.mk.bee.game.config.GameInfo;
import dk.mk.bee.game.exception.IllegalMapSize;
import dk.mk.bee.ui.config.VisualInfo;

public class GameMap {
    private GameObject[][] map;
    private int mapWidth;
    private int mapHeight;

    public GameMap() {
        //Initialize map
        if (GameInfo.GAME_SQUARE_COUNT_HORIZONTAL() * VisualInfo.SQUARE_SIZE() > VisualInfo.WINDOW_WIDTH() ||
                        GameInfo.GAME_SQUARE_COUNT_VERTICAL() * VisualInfo.SQUARE_SIZE() > VisualInfo.WINDOW_HEIGHT()) {
            Logger.warnLn("Map is so large it will be drawn out of screen");
            throw new IllegalMapSize(); //TODO how to handle? Map is going out of screen
        }

        this.mapHeight = GameInfo.GAME_SQUARE_COUNT_VERTICAL();
        this.mapWidth = GameInfo.GAME_SQUARE_COUNT_HORIZONTAL();
        this.map = new GameObject[mapHeight][mapWidth];

        //Fill map with border + blank
        initializeBlankBorder();
    }

    /** Initializes the map: creates the border and the empty play space. */
    private void initializeBlankBorder() {
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {

                if (x == 0 || y == 0) {
                    map[y][x] = new GameStructure(true);
                } else if (x == mapWidth-1 || y == mapHeight-1) { //TODO look into what it is reporting
                    map[y][x] = new GameStructure(true);
                } else {
                    map[y][x] = new GameStructure(false);
                }
            }
        }
    }

    public GameObject[][] map() {
        return map;
    }

    public int width() {
        return mapWidth;
    }

    public int height() {
        return mapHeight;
    }
}
