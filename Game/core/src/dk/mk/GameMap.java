package dk.mk;

import com.badlogic.gdx.graphics.Texture;
import dk.mk.gameObjects.*;

import java.util.ArrayList;

public class GameMap {

    private GameObject[][] map;
    private int mapWidth;
    private int mapHeight;

    private int beeCount = 0;
    private int hiveCount = 0;
    private int flowerCount = 0;

    private ArrayList<Vector2> hiveLocations = new ArrayList<Vector2>();
    private ArrayList<Vector2> flowerLocations = new ArrayList<Vector2>();
    private ArrayList<Vector2> beeLocations = new ArrayList<Vector2>();

    public GameMap(SpawnMethods.SPAWN_TYPE spawn_type) {

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

        //Fill map with game elements
        new SpawnMethods(spawn_type, map);

        updatePacket(map);
    }

    /** Initializes the map: creates the border and the empty play space. */
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
    }

    /** Used to update the data contained by this class. */
    public void updatePacket(){
        GameObject currentCell;

        //Runs through the map
        for(int y = 0; y < map.length; y++){
            for(int x = 0; x < map[0].length; x++){

                currentCell = map[y][x];

                //Most cells is GameStructure, so no further checks is needed.
                if(currentCell instanceof GameStructure) continue;

                if(currentCell instanceof Bee){
                    beeCount++;
                    beeLocations.add(new Vector2(y, x)); //TODO bug?
                } else if(currentCell instanceof Hive){
                    hiveCount++;
                    hiveLocations.add(new Vector2(x, y));
                } else if(currentCell instanceof Flower){
                    flowerCount++;
                    flowerLocations.add(new Vector2(x, y));
                }
            }
        }
    }

    public int getBeeCount() {
        return beeCount;
    }

    public int getHiveCount() {
        return hiveCount;
    }

    public int getFlowerCount() {
        return flowerCount;
    }

    public ArrayList<Vector2> getHiveLocations() {
        return hiveLocations;
    }

    public ArrayList<Vector2> getFlowerLocations() {
        return flowerLocations;
    }

    public ArrayList<Vector2> getBeeLocations() {
        return beeLocations;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public Texture getTextureFromCoords(int x, int y){
        return map[y][x].getTexture();
    }
}
