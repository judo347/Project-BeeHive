package dk.mk;

import dk.mk.gameObjects.*;

import java.util.ArrayList;

public class GameTickPacket {

    private int beeCount = 0;
    private int hiveCount = 0;
    private int flowerCount = 0;

    private ArrayList<Vector2> hiveLocations = new ArrayList<Vector2>();
    private ArrayList<Vector2> flowerLocations = new ArrayList<Vector2>();
    private ArrayList<Vector2> beeLocations = new ArrayList<Vector2>();

    public GameTickPacket(GameObject[][] map) {
        updatePacket(map);
    }

    /** Used to update the data contained by this class.
     * @param map the map of which data should be updated from. */
    public void updatePacket(GameObject[][] map){
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
}
