package dk.mk;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;


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

        public void changeType(CellType cellType){
            this.type = cellType;
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

        testSpawn();
    }

    private void testSpawn(){
        map[25][25].changeType(CellType.HIVE);
        map[24][25].changeType(CellType.BEE);
        map[26][25].changeType(CellType.BEE);

        map[4][4].changeType(CellType.FLOWER);
        map[5][4].changeType(CellType.FLOWER);
        map[12][30].changeType(CellType.FLOWER);
        map[6][23].changeType(CellType.FLOWER);
        map[22][4].changeType(CellType.FLOWER);
        map[32][32].changeType(CellType.FLOWER);
        map[74][32].changeType(CellType.FLOWER);
        map[32][74].changeType(CellType.FLOWER);
    }

    public void render(SpriteBatch batch){
        for(int y = 0; y < mapHeight; y++){
            for(int x = 0; x < mapWidth; x++){
                batch.draw(map[y][x].type.getTexture(), x * GameInfo.SQUARE_SIZE, y * GameInfo.SQUARE_SIZE, GameInfo.SQUARE_SIZE, GameInfo.SQUARE_SIZE);
            }
        }
    }

    public void tick(){
        //TODO

        //Pre-game check: Is there at least one hive and two bees?
        int beeCount = 0;
        int hiveCount = 0;
        Cell currentCell;

        for(int y = 0; y < mapHeight; y++){
            for(int x = 0; x < mapWidth; x++){

                currentCell = map[y][x];

                if(currentCell.type == CellType.BEE)
                    beeCount++;

                if(currentCell.type == CellType.HIVE)
                    hiveCount++;
            }
        }

        if(beeCount < 2 || hiveCount < 1)
            throw new IllegalGameStart(beeCount, hiveCount);

        //---------------------------------------------------------------------------------------------
        //Queen Rule: If two bees is near a hive, and there is no queen on the way or present,
        //they will start creating a new queen. //TODO ATM NO TIME TO CREATE

        //Find POS of hives.
        ArrayList<int[]> hives = new ArrayList();
        for(int y = 0; y < mapHeight; y++){
            for(int x = 0; x < mapWidth; x++){

                currentCell = map[y][x];

                if(currentCell.type == CellType.HIVE)
                    hives.add(new int[]{x, y});
            }
        }


        for(int[] hive : hives){
            if(checkSurroundings(CellType.QUEEN, 0, hive[0], hive[1])){ //Check if there is no queen around the hive

                if(checkSurroundings(CellType.BEE, 2, hive[0], hive[1])){ //Check if there is two bees near hive

                    int[] emptyCell = findEmptyCell(hive[0], hive[1]);  //Spawn queen on empty spot
                    map[emptyCell[1]][emptyCell[0]].changeType(CellType.QUEEN);
                }
            }
        }

        //---------------------------------------------------------------------------------------------

        

        System.out.println(hives.size());
        System.out.println("sek");
    }

    /** Checks the surrounding tiles of a position for a specific type.
     * @param type type to check for.
     * @param count how many to check for
     * @param x coordinate x.
     * @param y coordinate y.
     * @return true if type is around x,y. */
    private boolean checkSurroundings(CellType type, int count, int x, int y){

        ArrayList<Cell> surrondingCells = new ArrayList<Cell>();

        surrondingCells.add(map[y+1][x]);
        surrondingCells.add(map[y+1][x+1]);
        surrondingCells.add(map[y][x+1]);
        surrondingCells.add(map[y-1][x+1]);
        surrondingCells.add(map[y-1][x]);
        surrondingCells.add(map[y-1][x-1]);
        surrondingCells.add(map[y][x-1]);
        surrondingCells.add(map[y+1][x-1]);

        int counter = 0;

        for(Cell cell : surrondingCells)
            if(cell.type == type)
                counter++;

        return counter == count;
    }

    //TODO BUG: does not check layer > 1 correctly.
    /** Searches for an empty cell near coords, returns coords of empty cell. */
    private int[] findEmptyCell(int x, int y){

        int layer = 1;

        while(true) {

            if (map[y + layer][x].type == CellType.BLANK)
                return new int[]{y + layer, x};

            if (map[y + layer][x + layer].type == CellType.BLANK)
                return new int[]{y + layer, x + layer};

            if (map[y][x + layer].type == CellType.BLANK)
                return new int[]{y, x + layer};

            if (map[y - layer][x + layer].type == CellType.BLANK)
                return new int[]{y - layer, x + layer};

            if (map[y - layer][x].type == CellType.BLANK)
                return new int[]{y - layer, x};

            if (map[y - layer][x - layer].type == CellType.BLANK)
                return new int[]{y - layer, x - layer};

            if (map[y][x - layer].type == CellType.BLANK)
                return new int[]{y, x - layer};

            if (map[y + layer][x - layer].type == CellType.BLANK)
                return new int[]{y + layer, x - layer};

            layer++;
        }
    }
}
