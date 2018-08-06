package dk.mk;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class GameMap {

    public enum CellType{

        BEE("bee.png"), QUEEN("queen.png"), HIVE("hive.png"), FLOWER("flower.png"), BLANK("blank.png"), BORDER("border.png");

        private Texture texture;

        CellType(String imagePath) {

            if(imagePath != null)
                this.texture = new Texture(imagePath);
        }

        public Texture getTexture() {
            return texture;
        }
    }

    private class Cell{

    }

    private Cell[][] map;
    private int mapWidth;
    private int mapHeight;

    public GameMap(){

        //Initialize map
        if(GameInfo.WINDOW_HEIGHT % GameInfo.SQUARE_SIZE != 0 || GameInfo.WINDOW_WIDTH % GameInfo.SQUARE_SIZE != 0)
            throw new IllegalMapSize();
        else{
            this.mapHeight = GameInfo.WINDOW_HEIGHT / GameInfo.SQUARE_SIZE;
            this.mapWidth = GameInfo.WINDOW_WIDTH / GameInfo.SQUARE_SIZE;
        }

        map = new Cell[mapHeight][mapWidth];
    }

    public void render(SpriteBatch batch){
        //TODO
    }

    public void tick(){
        //TODO
    }
}
