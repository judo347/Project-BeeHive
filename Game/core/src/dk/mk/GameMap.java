package dk.mk;

import com.badlogic.gdx.graphics.Color;

public class GameMap {

    public enum CellType{

        BEE(Color.YELLOW), QUEEN(Color.BROWN), HIVE(Color.GRAY), FLOWER(Color.RED), NOTHING(Color.CHARTREUSE), BORDER(Color.BLACK);

        private final Color color;

        CellType(Color color) {
            this.color = color;
        }
    }

    private class Cell{

    }

    private Cell[][] map;
    private int mapWidth;
    private int mapHeight;

    public GameMap(){

        //Initialize map
        if(GameInfo.WINDOW_HEIGHT % 10 != 0 || GameInfo.WINDOW_WIDTH % 10 != 0)
            throw new IllegalMapSize();
        else{
            this.mapHeight = GameInfo.WINDOW_HEIGHT / 10;
            this.mapWidth = GameInfo.WINDOW_WIDTH / 10;
        }

        map = new Cell[mapHeight][mapWidth];
    }
}
