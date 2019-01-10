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

    private ArrayList<Hive> allHives = new ArrayList<Hive>();
    private ArrayList<Flower> allFlowers = new ArrayList<Flower>();
    private ArrayList<Bee> allBees = new ArrayList<Bee>();

    public GameTickPacket(GameObject[][] map) {
        updatePacket(map);
    }

    /** Used to update the data contained by this class.
     * @param map the map of which data should be updated from. */
    public void updatePacket(GameObject[][] map){
        GameObject currentCell;
        clearData();

        //Runs through the map
        for(int y = 0; y < map.length; y++){
            for(int x = 0; x < map[0].length; x++){

                currentCell = map[y][x];

                //Most cells is GameStructure, so no further checks is needed.
                if(currentCell instanceof GameStructure) continue;

                if(currentCell instanceof Bee){
                    beeCount++;
                    beeLocations.add(new Vector2(x, y));
                    allBees.add((Bee)currentCell);
                } else if(currentCell instanceof Hive){
                    hiveCount++;
                    hiveLocations.add(new Vector2(x, y));
                    allHives.add((Hive)currentCell);
                } else if(currentCell instanceof Flower){
                    flowerCount++;
                    flowerLocations.add(new Vector2(x, y));
                    allFlowers.add((Flower)currentCell);
                }
            }
        }
    }

    private void clearData(){
        beeCount = 0;
        hiveCount = 0;
        flowerCount = 0;

        hiveLocations = new ArrayList<Vector2>();
        flowerLocations = new ArrayList<Vector2>();
        beeLocations = new ArrayList<Vector2>();

        allHives = new ArrayList<Hive>();
        allBees = new ArrayList<Bee>();
        allFlowers = new ArrayList<Flower>();
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

    public ArrayList<Hive> getAllHives() {
        return allHives;
    }

    public ArrayList<Flower> getAllFlowers() {
        return allFlowers;
    }

    public ArrayList<Bee> getAllBees() {
        return allBees;
    }
}
