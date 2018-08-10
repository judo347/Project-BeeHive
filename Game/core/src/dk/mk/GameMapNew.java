package dk.mk;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.mk.gameObjects.*;

import java.util.ArrayList;

public class GameMapNew {

    private GameObject[][] map;
    private int mapWidth;
    private int mapHeight;

    public GameMapNew() {

        //Initialize map
        if(GameInfo.WINDOW_HEIGHT % GameInfo.SQUARE_SIZE != 0 || GameInfo.WINDOW_WIDTH % GameInfo.SQUARE_SIZE != 0)
            throw new IllegalMapSize();
        else{
            this.mapHeight = (GameInfo.WINDOW_HEIGHT / GameInfo.SQUARE_SIZE) - 1;
            this.mapWidth = (GameInfo.WINDOW_WIDTH / GameInfo.SQUARE_SIZE) - 1;
        }

        map = new GameObject[mapHeight][mapWidth];

        //Fill map with border + blank
        initializeBlankBorder();

    }

    private void initializeBlankBorder(){

        for(int y = 0; y < mapHeight; y++){
            for(int x = 0; x < mapWidth; x++){

                if(x == 0 || y == 0){
                    map[y][x] = new GameStructure(true);
                }else if(x == mapWidth || y == mapHeight){
                    map[y][x] = new GameStructure(true);
                }else{
                    map[y][x] = new GameStructure(false);
                }
            }
        }

        testSpawn();
    }

    private void testSpawn(){

        //Hive one
        map[25][25] = new Hive();
        map[24][25] = new Bee();
        map[26][25] = new Bee();

        //Hive two
        map[70][70] = new Hive();
        map[71][70] = new Bee();
        map[71][71] = new Bee();
        map[70][71] = new Bee();

        //Flowers
        map[4][4] = new Flower();
        map[5][4] = new Flower();
        map[12][30] = new Flower();
        map[6][23] = new Flower();
        map[22][4] = new Flower();
        map[32][32] = new Flower();
        map[74][32] = new Flower();
        map[32][74] = new Flower();
    }

    public void render(SpriteBatch batch){
        for(int y = 0; y < mapHeight; y++){
            for(int x = 0; x < mapWidth; x++){
                batch.draw(map[y][x].getTexture(), x * GameInfo.SQUARE_SIZE, y * GameInfo.SQUARE_SIZE, GameInfo.SQUARE_SIZE, GameInfo.SQUARE_SIZE);
            }
        }
    }

    public void tick(){

        preGameCheck();
        queenRuleCheck();
        //harvestRuleCheck();



        //System.out.println("Hives " + hives.size());
        //System.out.println("Bees " + beeCount);
        System.out.println("sek");
    }

    private void preGameCheck(){

        //Pre-game check: Is there at least one hive and two bees?
        int beeCount = 0;
        int hiveCount = 0;
        GameObject currentCell;

        for(int y = 0; y < mapHeight; y++){
            for(int x = 0; x < mapWidth; x++){

                currentCell = map[y][x];

                if(currentCell instanceof Bee)
                    beeCount++;

                if(currentCell instanceof Hive)
                    hiveCount++;
            }
        }

        if(beeCount < 2 || hiveCount < 1)
            throw new IllegalGameStart(beeCount, hiveCount);

        //System.out.println("PregameCheck: HiveCount = " + hiveCount + " BeeCount = " + beeCount + ".");
    }

    private void queenRuleCheck(){
        //Queen Rule: If two bees is near a hive, and there is no queen on the way or present,
        //they will start creating a new queen. //TODO ATM NO TIME TO CREATE

        //Find POS of hives.

        ArrayList<int[]> hives = new ArrayList();
        GameObject currentCell;

        for(int y = 0; y < mapHeight; y++){
            for(int x = 0; x < mapWidth; x++){

                currentCell = map[y][x];

                if(currentCell instanceof Hive)
                    hives.add(new int[]{x, y});
            }
        }

        for(int[] hive : hives){
            if(checkSurroundings(new Queen(), hive[0], hive[1]) == 0){ //Check if there is no queen around the hive

                //System.out.println("There is no queen!");

                if(checkSurroundings(new Bee(), hive[0], hive[1]) >= 2){ //Check if there is two bees near hive

                    //System.out.println("There are at least two bees!");

                    int[] emptyCell = findEmptyCell(hive[0], hive[1]);  //Spawn queen on empty spot
                    map[emptyCell[1]][emptyCell[0]] = new Queen();
                }
            }
        }
    }

    private void harvestRuleCheck(){

        //Harvest rule: If the hive contains a queen, then the bee will choose a random direction and
        //start searching for a flower. When found the bee will harvest it, and return to the hive.

        //If contains queen, fly in psudo random direction

        //Find hive locations
        ArrayList<int[]> hiveLocations = getHiveLocations();

        //

    }

    /** Searches the map and returns the coordinates for the hives. */
    private ArrayList<int[]> getHiveLocations(){

        ArrayList<int[]> hiveLocations = new ArrayList<int[]>();

        for(int y = 0; y < mapHeight; y++){
            for(int x = 0; x < mapWidth; x++){

                if(map[y][x] instanceof Hive)
                    hiveLocations.add(new int[]{x, y});
            }
        }

        return hiveLocations;
    }

    private int checkSurroundings(GameObject requestedGameObject, int x, int y){

        ArrayList<GameObject> surroundingCells = new ArrayList<GameObject>();

        surroundingCells.add(map[y+1][x]);
        surroundingCells.add(map[y+1][x+1]);
        surroundingCells.add(map[y][x+1]);
        surroundingCells.add(map[y-1][x+1]);
        surroundingCells.add(map[y-1][x]);
        surroundingCells.add(map[y-1][x-1]);
        surroundingCells.add(map[y][x-1]);
        surroundingCells.add(map[y+1][x-1]);

        int counter = 0;

        for(GameObject gameObject : surroundingCells)
            if(gameObject.getClass() == requestedGameObject.getClass())
                counter++;

        return counter;
    }

    //TODO BUG: does not check layer > 1 correctly.
    /** Searches for an empty cell near coords, returns coords of empty cell. */
    private int[] findEmptyCell(int x, int y){

        int layer = 1;

        while(true) {

            if(map[y + layer][x] instanceof GameStructure)
                if(!((GameStructure)map[y + layer][x]).isSolid())
                    return new int[]{x, y + layer};


            if (map[y + layer][x + layer] instanceof GameStructure)
                if(!((GameStructure)map[y + layer][x + layer]).isSolid())
                    return new int[]{x + layer, y + layer};

            if (map[y][x + layer] instanceof GameStructure)
                if(!((GameStructure)map[y][x + layer]).isSolid())
                    return new int[]{x + layer, y};

            if (map[y - layer][x + layer] instanceof GameStructure)
                if(!((GameStructure)map[y - layer][x + layer]).isSolid())
                    return new int[]{x + layer, y - layer};

            if (map[y - layer][x] instanceof GameStructure)
                if(!((GameStructure)map[y - layer][x]).isSolid())
                    return new int[]{x, y - layer};

            if (map[y - layer][x - layer] instanceof GameStructure)
                if(!((GameStructure)map[y - layer][x - layer]).isSolid())
                    return new int[]{x - layer, y - layer};

            if (map[y][x - layer] instanceof GameStructure)
                if(!((GameStructure)map[y][x - layer]).isSolid())
                    return new int[]{x - layer, y};

            if (map[y + layer][x - layer] instanceof GameStructure)
                if(!((GameStructure)map[y + layer][x - layer]).isSolid())
                    return new int[]{x - layer, y + layer};

            layer++;
        }
    }
}
