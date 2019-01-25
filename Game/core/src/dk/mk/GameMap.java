package dk.mk;

import com.badlogic.gdx.graphics.Texture;
import dk.mk.gameObjects.*;

import java.util.ArrayList;

public class GameMap {

    private GameObject[][] map;
    private int mapWidth;
    private int mapHeight;

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

        updatePacket();
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
                    beeLocations.add(new Vector2(x, y));
                } else if(currentCell instanceof Hive){
                    hiveLocations.add(new Vector2(x, y));
                } else if(currentCell instanceof Flower){
                    flowerLocations.add(new Vector2(x, y));
                }
            }
        }
    }


    //-----------METHODS THAT CHANGE THE MAP-------------

    /** Removes the gameObject on the given (x,y) coordinate.
     * @param location the coordinates of the involved tile/GameObject.
     * @return false if the given coordinates corrospond to a GameStructure. */
    public boolean removeGameObject(Vector2 location){

        //TODO OUT OF BOUNDS CHECK!!

        GameObject currentObj = getGameObjectFromCoords(location);

        if(currentObj instanceof GameStructure)
            return false;
        else if(currentObj instanceof Hive){

            hiveLocations.remove(location);
            map[location.y][location.x] = new GameStructure(false);

            //TODO WHAT ABOUT QUEENS AND OWNED BEES?

        }else if(currentObj instanceof Bee){

            beeLocations.remove(location);
            map[location.y][location.x] = new GameStructure(false);

            //TODO THE BEES SHOULD ALSO BE REMOVE FROM THE HIVE THAT OWNS IT!!

        }else if(currentObj instanceof Queen){
            //TODO SHOULD THE QUEEN NOT BE REPRESENTED IN ANOTHER CLASS / PLACE?
            map[location.y][location.x] = new GameStructure(false);
        }else if(currentObj instanceof Flower){
            flowerLocations.remove(location);
            map[location.y][location.x] = new GameStructure(false);
        }else
            throw new IllegalStateException("THIS SHOULD NEVER HAVE HAPPENED! GameMap -> removeGameObject"); //THIS SHOULD NEVER HAPPEN!!!

        return true;
    }

    /** Adds the given gameObject to the given location.
     * @param location the location which is desired for the object.
     * @param gameObject the object to be added.
     * @return true if the object could be added. */
    public boolean addGameObject(Vector2 location, GameObject gameObject){

        //Check the location
        //TODO OUT OF BOUNDS CHECK!!

        GameObject destinationObject = getGameObjectFromCoords(location);

        if(destinationObject instanceof GameStructure){
            if(((GameStructure) destinationObject).isSolid())
                return false; //BORDER HAS BEEN HIT
        }

        //Add the gameObject
        map[location.y][location.x] = gameObject;

        if(gameObject instanceof Bee)
            beeLocations.add(location);
        else if(gameObject instanceof Flower)
            flowerLocations.add(location);
        else if(gameObject instanceof Hive)
            hiveLocations.add(location);

        return true;
    }

    /** Moves the object from the given location to the given location
     * @return true if gameObject was moved. */
    public boolean moveGameObject(Vector2 fromLocation, Vector2 toLocation){

        GameObject objectToBeMoved = getGameObjectFromCoords(fromLocation);

        if(addGameObject(toLocation, objectToBeMoved)){ //TODO does this remove and add the correct ones in arraylist
            removeGameObject(fromLocation);
            return true;
        }else
            return false;
    }

    //---------------------------------------------------



    public int getBeeCount() {
        return getBeeLocations().size();
    }

    public int getHiveCount() {
        return getHiveLocations().size();
    }

    public int getFlowerCount() {
        return getFlowerLocations().size();
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

    public GameObject getGameObjectFromCoords(Vector2 location){
        return map[location.y][location.x];
    }
}
