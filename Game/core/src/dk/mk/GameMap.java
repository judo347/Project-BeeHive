package dk.mk;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.mk.gameObjects.*;

import java.util.*;

public class GameMap {

    /** This enum is used as the directions for the bees. */
    public enum Direction{
        NORTH(0, 1), NORTHEAST(1, 1), EAST(1, 0), SOUTHEAST(1, -1), SOUTH(0, -1), SOUTHWEST(-1, -1), WEST(-1, 0), NORTHWEST(-1, 1);

        private Vector2 coordinates;

        Direction(int x, int y) {
            this.coordinates = new Vector2(x, y);
        }

        public Vector2 getCoordinates() {
            return new Vector2(coordinates);
        }

        public static LinkedList<Direction> getLinkedList(){
            return new LinkedList<Direction>(Arrays.asList(Direction.values()));
        }

        public Direction findOppositeDirection(){

            for(Direction newDirection : Direction.values()){
                if((newDirection.coordinates.x * -1) == this.coordinates.x && (newDirection.coordinates.y * -1) == this.coordinates.y)
                    return newDirection;
            }

            return null; //Should not get here
        }
    }

    private GameObject[][] map;
    private int mapWidth;
    private int mapHeight;

    public GameMap() {

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
        SpawnMethods.testSpawn(map);
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

    public void render(SpriteBatch batch){
        for(int y = 0; y < mapHeight; y++){
            for(int x = 0; x < mapWidth; x++){
                batch.draw(map[y][x].getTexture(), x * GameInfo.SQUARE_SIZE, y * GameInfo.SQUARE_SIZE, GameInfo.SQUARE_SIZE, GameInfo.SQUARE_SIZE);
            }
        }
    }

    /** The tick of the game. Calls the rules and progresses the game accordingly. */
    public void tick(){
        preGameCheck();
        queenRuleCheck();
        harvestRuleCheck();
    }

    /** The pre-game check: is there at least two bees and one hive? */
    private void preGameCheck(){

        int beeCount = 0;
        int hiveCount = 0;
        GameObject currentCell;

        //Runs through map and counts bees and hives
        for(int y = 0; y < mapHeight; y++){
            for(int x = 0; x < mapWidth; x++){

                currentCell = map[y][x];

                if(currentCell instanceof Bee)
                    beeCount++;

                if(currentCell instanceof Hive)
                    hiveCount++;
            }
        }

        //Check the requirement
        if(beeCount < 2 || hiveCount < 1)
            throw new IllegalGameStart(beeCount, hiveCount);
    }

    /** Queen rule check: if there is two or more bees near a hive and
     *  there is no queen on the way or present at hive. Then create one. */
    private void queenRuleCheck(){

        ArrayList<Vector2> hives = new ArrayList<Vector2>();
        GameObject currentCell;

        //Find coordinates of hives. Searches the map and saves the cells that are hives.
        for(int y = 0; y < mapHeight; y++){
            for(int x = 0; x < mapWidth; x++){

                currentCell = map[y][x];

                if(currentCell instanceof Hive)
                    hives.add(new Vector2(x, y));
            }
        }

        //Check hives for queen. If no = spawn queen.
        for(Vector2 hive : hives){
            if(checkSurroundings(new Queen(), hive) == 0){ //Check if there is no queen around the hive
                if(checkSurroundings(new Bee(0,0), hive) >= 2){ //Check if there is at least two bees near hive
                    Vector2 emptyCell = findEmptyCell(hive);  //Find empty cell
                    map[emptyCell.y][emptyCell.x] = new Queen(); //Spawn queen in empty cell
                }
            }
        }
    }

    /** Harvest rule check: if the hive contains a queen, then the bee will choose a psudo random direction and
     *  start searching for a flower. When found the bee will harvest it, and return to the hive*/
    private void harvestRuleCheck(){

        ArrayList<Vector2> hiveLocations = getGameObjectLocations(new Hive()); //Find hive locations
        ArrayList<Vector2> flowerLocations = getGameObjectLocations(new Flower()); //Find flower locations
        ArrayList<Vector2> beeLocations = getGameObjectLocations(new Bee(0,0)); //Find bee locations

        //Does bees near hive have pollen? Then spawn new bee.
        isBeeWithPollenNearHiveEqualsSpawn(hiveLocations);

        //Is bee near flower? Give bee pollen.
        isBeeWithPollenNearFlowerGivePollen(flowerLocations);

        //AT THIS POINT: BEES HAVE GIVEN OR RECEIVED POLLEN. ITS NOW TIME TO MOVE.
        moveBees(beeLocations, flowerLocations);
    }

    /** Checks if a bee near a hive has pollen. If true remove pollen and spawn new bee.
     *  @param hiveLocations an arrayList containing hive locations. */
    private void isBeeWithPollenNearHiveEqualsSpawn(ArrayList<Vector2> hiveLocations){

        for(Vector2 hiveCoords : hiveLocations){
            ArrayList<Bee> beesAroundHive = getBeesInProximityCoordinate(hiveCoords); //Get arrayList of bees around current hive

            //Check all bees around the current hive for pollen.
            for(Bee bee : beesAroundHive){
                if(bee.hasPollen()){ //If the bee has pollen. Spawn new bee in empty cell;

                    //Spawn new bee at hive
                    Vector2 emptyLocation = findEmptyCell(hiveCoords);
                    map[emptyLocation.y][emptyLocation.x] = new Bee(hiveCoords.x, hiveCoords.y);

                    //Remove pollen from bee
                    bee.removePollen();
                }
            }
        }
    }

    /** Checks if a bee is near a flower. If true give pollen to bee.
     *  @param flowerLocations an arrayList containing flower locations. */
    private void isBeeWithPollenNearFlowerGivePollen(ArrayList<Vector2> flowerLocations){

        for(Vector2 flowerCoords : flowerLocations){
            ArrayList<Bee> beesAroundFlower = getBeesInProximityCoordinate(flowerCoords); //Get bees around flower.

            //Give pollen to all bees around current flower.
            for(Bee bee : beesAroundFlower){
                bee.givePollen();
            }
        }
    }

    /**
     *  @param beesLocations an arrayList containing all bee locations.
     *  @param flowerLocations an arrayList containing all flower locations. */
    private void moveBees(ArrayList<Vector2> beesLocations, ArrayList<Vector2> flowerLocations){

        for(Vector2 beeLocation : beesLocations){

            Bee currentBee = (Bee)map[beeLocation.y][beeLocation.x];

            if(currentBee.hasPollen()){ //If bee has pollen. Then GoTowardsHive

                //Get sorted linkedList of next coordinates and all eight directions.
                LinkedList<Vector2> coordinates = getSortedSetOfDirectionalCoordinates(currentBee.getHiveCoordinates(), beeLocation);

                //Check all 8 directions and take the first available one (first empty cell)
                for(int i = 0; i < 7; i++){

                    Vector2 coordinate = coordinates.get(i);

                    if(moveBee(new Vector2(beeLocation), new Vector2(coordinate)))
                        break;
                }
            }else{ //GoAwayFromHive

                //If the bees currentHuntDirection is not set. Give it one
                if(currentBee.getCurrentHuntDirection() == null){

                    //Calculate the direction away from hive, give that to the bee
                    currentBee.setCurrentHuntDirection(findDirectionFromCoords(currentBee.getHiveCoordinates(), beeLocation).findOppositeDirection());
                }

                //If bee is # moves from flower, set direction to that way
                Vector2 nearestFlower = isBeeNearFlower(beeLocation, flowerLocations, Bee.BEE_FLOWER_ALERT_DIST);

                if(nearestFlower != null)
                    currentBee.setCurrentHuntDirection(findDirectionFromCoords(nearestFlower, beeLocation)); //Get direction of flower, and set that to the be the bees direction

                //Get list of directions in bees desired direction
                LinkedList<Vector2> coordinates = getSortedSetOfDirectionalCoordinates(new Vector2(beeLocation.x + currentBee.getCurrentHuntDirection().coordinates.x,
                                                                                                    beeLocation.y + currentBee.getCurrentHuntDirection().coordinates.y),
                                                                                                        beeLocation);

                //move bee in that direction if possible, or continues on list
                for(int i = 0; i < 7; i++){

                    Vector2 coordinate = coordinates.get(i);

                    if(moveBee(new Vector2(beeLocation), new Vector2(coordinate)))
                        break;
                }
            }
        }
    }

    /** Find the coordinates of the closest flower.
     * @param beeLocation the coordinates of the bee
     * @param flowersLocations ArrayList with coordinates of all flowers
     * @param maxDist max allowed dist to flowers
     * @return finds the closest flower within reach (maxDist), or null if non is found. */
    private Vector2 isBeeNearFlower(Vector2 beeLocation, ArrayList<Vector2> flowersLocations, int maxDist){

        ArrayList<double[]> inReachFlowers = new ArrayList<double[]>();

        for(Vector2 flowerVector : flowersLocations){

            //Calculate distance from bee to flower
            int xDiff = Math.abs(flowerVector.x - beeLocation.x);
            int yDiff = Math.abs(flowerVector.y - beeLocation.y);

            //Create list of flowers within reach, less or equal to maxDist
            //Get list of flowers in reach
            if(xDiff <= maxDist && yDiff <= maxDist){
                double distance = Math.sqrt(Math.pow(flowerVector.x, 2) + Math.pow(flowerVector.y, 2)); //Calculate distance in double
                inReachFlowers.add(new double[]{flowerVector.x, flowerVector.y, distance}); //Save the distance with the valid flower
            }
        }

        //If size of list is not 0, then find the closest flower
        if(inReachFlowers.size() > 0){

            //double[] closestFlower = new double[]{inReachFlowers.get(0)[0], inReachFlowers.get(0)[1], inReachFlowers.get(0)[2]};
            double[] closestFlower = inReachFlowers.get(0);

            //Does array contain more than 1? Else return that one
            if(inReachFlowers.size() > 2){

                //Search for the closest one
                for(int i = 1; i < inReachFlowers.size(); i++){

                    //If closer flower is found, save that one
                    if(inReachFlowers.get(i)[2] < closestFlower[2])
                        closestFlower = inReachFlowers.get(i);
                }

            }


            return new Vector2((int)closestFlower[0], (int)closestFlower[1]);
        }

        return null;
    }

    /** Moves the bee. Return true if bee was moved.
     *  @param myCoords coordinates of the bee to be moved.
     *  @param destCoords coordinates of the desired destination of the bee.
     *  @return true if bee was moved. */
    private boolean moveBee(Vector2 myCoords, Vector2 destCoords){

        try{
            ////Is destination empty check?
            if(map[destCoords.y][destCoords.x] instanceof GameStructure){
                if(!((GameStructure) map[destCoords.y][destCoords.x]).isSolid()){

                    //MOVE
                    map[destCoords.y][destCoords.x] = new Bee(((Bee)map[myCoords.y][myCoords.x]).getHiveCoordinates().x, ((Bee)map[myCoords.y][myCoords.x]).getHiveCoordinates().y);
                    if(((Bee)map[myCoords.y][myCoords.x]).hasPollen()) //Keep pollen state
                        ((Bee)map[destCoords.y][destCoords.x]).givePollen();
                    map[myCoords.y][myCoords.x] = new GameStructure(false); //Delete old bee

                    return true;
                }
            }
        }catch (ArrayIndexOutOfBoundsException e){ //If request is outside playing field.
            return false;
        }

        return false;
    }

    /** Finds a direction from myCoords to destCoords. */
    private Direction findDirectionFromCoords(Vector2 dest, Vector2 me){

        int xDifference =  dest.x - me.x;
        int yDifference =  dest.y - me.y;

        xDifference = (xDifference == 0) ? 0 : (xDifference < 0) ? -1 : 1; //Reduce to -1, 0, 1
        yDifference = (yDifference == 0) ? 0 : (yDifference < 0) ? -1 : 1; //Reduce to -1, 0, 1

        Direction firstDirection = null;

        //Find the first direction
        for(Direction direction : Direction.values()){
            if(direction.coordinates.x == xDifference && direction.coordinates.y == yDifference)
                firstDirection = direction;
        }

        return firstDirection;
    }

    /** @return a list of sorted directions created from the given vectors. */
    private LinkedList<Vector2> getSortedSetOfDirectionalCoordinates(Vector2 dest, Vector2 me){

        //Find the first direction
        Direction firstDirection = findDirectionFromCoords(dest, me);

        LinkedList<Direction> directionLinkedList = Direction.getLinkedList();

        //Cycles through the list until firstDirection is first in list
        while(firstDirection != directionLinkedList.peek()){
            directionLinkedList.addLast(directionLinkedList.poll());
        }

        //Convert list to have the right order
        LinkedList<Direction> directionLinkedListOrdered = new LinkedList<Direction>();

        while(directionLinkedList.size() > 0){
            directionLinkedListOrdered.add(directionLinkedList.pollFirst());
            directionLinkedListOrdered.add(directionLinkedList.pollLast());
        }

        return convertRelativeToMyPos(directionLinkedListOrdered, me);
    }

    /** @return linkedList that contains coordinates relative to me-vector. */
    private LinkedList<Vector2> convertRelativeToMyPos(LinkedList<Direction> list, Vector2 me){

        LinkedList<Vector2> relativeList = new LinkedList<Vector2>();

        //Create copy of original list and convert to Vector2 list
        for (Direction direction : list)
            relativeList.addLast(direction.getCoordinates());

        //Calculate relative vectors
        for (Vector2 vector : relativeList) {
            vector.x = vector.x + me.x;
            vector.y = vector.y + me.y;
        }

        return relativeList;
    }

    /** @return arrayList of bees surrounding given coordinate. */
    private ArrayList<Bee> getBeesInProximityCoordinate(Vector2 coordinates){

        ArrayList<Bee> bees = new ArrayList<Bee>();
        int x = coordinates.x;
        int y = coordinates.y;

        //Check all surrounding coordinates. If bee found, add to ArrayList
        if(map[y+1][x].getClass() == Bee.class)
            bees.add((Bee)map[y+1][x]);
        if(map[y+1][x+1].getClass() == Bee.class)
            bees.add((Bee)map[y+1][x+1]);
        if(map[y][x+1].getClass() == Bee.class)
            bees.add((Bee)map[y][x+1]);
        if(map[y-1][x+1].getClass() == Bee.class)
            bees.add((Bee)map[y-1][x+1]);
        if(map[y-1][x].getClass() == Bee.class)
            bees.add((Bee)map[y-1][x]);
        if(map[y-1][x-1].getClass() == Bee.class)
            bees.add((Bee)map[y-1][x-1]);
        if(map[y][x-1].getClass() == Bee.class)
            bees.add((Bee)map[y][x-1]);
        if(map[y+1][x-1].getClass() == Bee.class)
            bees.add((Bee)map[y+1][x-1]);

        return bees;
    }

    /** Searches the map and returns the coordinates for the requested game object. */
    private ArrayList<Vector2> getGameObjectLocations(GameObject gameObject){

        ArrayList<Vector2> gameObjectLocations = new ArrayList<Vector2>();

        //Go through map and add all requested GameObject-locations to the array
        for(int y = 0; y < mapHeight; y++){
            for(int x = 0; x < mapWidth; x++){

                if(map[y][x].getClass() == gameObject.getClass())
                    gameObjectLocations.add(new Vector2(x, y));
            }
        }

        return gameObjectLocations;
    }

    /** Searches the map and returns an arraylist of all bees. */
    private ArrayList<Bee> getAllBees(){

        ArrayList<Bee> bees = new ArrayList<Bee>();

        for(int y = 0; y < mapHeight; y++){
            for(int x = 0; x < mapWidth; x++){

                if(map[y][x].getClass() == Bee.class)
                    bees.add((Bee)map[y][x]);
            }
        }

        return bees;
    }

    /** @return number of requestedGameObject surrounding given coordinates. */
    private int checkSurroundings(GameObject requestedGameObject, Vector2 coords){

        ArrayList<GameObject> surroundingCells = new ArrayList<GameObject>();
        int x = coords.x;
        int y = coords.y;

        //Get all surrounding cells
        surroundingCells.add(map[y+1][x]);
        surroundingCells.add(map[y+1][x+1]);
        surroundingCells.add(map[y][x+1]);
        surroundingCells.add(map[y-1][x+1]);
        surroundingCells.add(map[y-1][x]);
        surroundingCells.add(map[y-1][x-1]);
        surroundingCells.add(map[y][x-1]);
        surroundingCells.add(map[y+1][x-1]);

        int counter = 0;

        //Count surrounding cells containing requested gameObject
        for(GameObject gameObject : surroundingCells)
            if(gameObject.getClass() == requestedGameObject.getClass())
                counter++;

        return counter;
    }
    /** Searches for an empty cell near given coordinates, returns coordinates of empty cell. */
    private Vector2 findEmptyCell(Vector2 coords){

        int x = coords.x;
        int y = coords.y;
        int layer = 1;

        while(true) {

            if(map[y + layer][x] instanceof GameStructure)
                if(!((GameStructure)map[y + layer][x]).isSolid())
                    return new Vector2(x, y + layer);


            if (map[y + layer][x + layer] instanceof GameStructure)
                if(!((GameStructure)map[y + layer][x + layer]).isSolid())
                    return new Vector2(x + layer, y + layer);

            if (map[y][x + layer] instanceof GameStructure)
                if(!((GameStructure)map[y][x + layer]).isSolid())
                    return new Vector2(x + layer, y);

            if (map[y - layer][x + layer] instanceof GameStructure)
                if(!((GameStructure)map[y - layer][x + layer]).isSolid())
                    return new Vector2(x + layer, y - layer);

            if (map[y - layer][x] instanceof GameStructure)
                if(!((GameStructure)map[y - layer][x]).isSolid())
                    return new Vector2(x, y - layer);

            if (map[y - layer][x - layer] instanceof GameStructure)
                if(!((GameStructure)map[y - layer][x - layer]).isSolid())
                    return new Vector2(x - layer, y - layer);

            if (map[y][x - layer] instanceof GameStructure)
                if(!((GameStructure)map[y][x - layer]).isSolid())
                    return new Vector2(x - layer, y);

            if (map[y + layer][x - layer] instanceof GameStructure)
                if(!((GameStructure)map[y + layer][x - layer]).isSolid())
                    return new Vector2(x - layer, y + layer);

            layer++;
        }
    }
}
