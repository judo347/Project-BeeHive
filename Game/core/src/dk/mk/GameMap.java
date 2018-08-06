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

        private CellType type;
        //TODO maybe coords is needed?

        public Cell(CellType type) {
            this.type = type;
        }
    }

    private Cell[][] map;
    private int mapWidth;
    private int mapHeight;

    public GameMap(){

        //Initialize map
        if(GameInfo.WINDOW_HEIGHT % GameInfo.SQUARE_SIZE != 0 || GameInfo.WINDOW_WIDTH % GameInfo.SQUARE_SIZE != 0)
            throw new IllegalMapSize();
        else{
            this.mapHeight = (GameInfo.WINDOW_HEIGHT / GameInfo.SQUARE_SIZE) - 1;
            this.mapWidth = (GameInfo.WINDOW_WIDTH / GameInfo.SQUARE_SIZE) - 1;
        }

        map = new Cell[mapHeight][mapWidth];

        //Fill map with border + blank
        initializeBlankBorder();
    }

    private void initializeBlankBorder(){

        for(int y = 0; y < mapHeight; y++){
            for(int x = 0; x < mapWidth; x++){

                if(x == 0 || y == 0){
                    map[y][x] = new Cell(CellType.BORDER);
                }else if(x == mapWidth || y == mapHeight){
                    map[y][x] = new Cell(CellType.BORDER);
                }else{
                    map[y][x] = new Cell(CellType.BLANK);
                }
            }
        }
    }

    public void render(SpriteBatch batch){
        //TODO

        for(int y = 0; y < mapHeight; y++){
            for(int x = 0; x < mapWidth; x++){

                batch.draw(map[y][x].type.getTexture(), x * GameInfo.SQUARE_SIZE, y * GameInfo.SQUARE_SIZE);
            }
        }

    }

    public void tick(){
        //TODO
    }
}
