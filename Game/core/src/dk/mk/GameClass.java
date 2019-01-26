package dk.mk;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.mk.gameObjects.*;
import dk.mk.SpawnMethods.*;

import java.util.*;

public class GameClass {

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

    GameMap gameMap;

    public GameClass(SPAWN_TYPE spawn_type) {
        this.gameMap = new GameMap(spawn_type);

    }

    public void render(SpriteBatch batch){
        for(int y = 0; y < gameMap.getMapHeight(); y++){
            for(int x = 0; x < gameMap.getMapWidth(); x++){
                batch.draw(gameMap.getTextureFromCoords(x, y), x * GameInfo.SQUARE_SIZE, y * GameInfo.SQUARE_SIZE, GameInfo.SQUARE_SIZE, GameInfo.SQUARE_SIZE);
            }
        }
    }

    /** The tick of the game. Calls the rules and progresses the game accordingly. */
    public void tick(float delta){
        gameMap.updatePacket();

        //TODO TICK ALL BEES
        preGameCheck(gameMap);
        beeTick(gameMap, delta);
        queenRuleCheck(gameMap);
        harvestRuleCheck(gameMap);
    }

    /** Used to tick the bees: update their lifetime and remove dead bees. */
    private void beeTick(GameMap gtp, float delta){
        ArrayList<Vector2> beeLocations = gtp.getBeeLocations();

        for (Vector2 beeLocation : beeLocations) {
            Bee currentBee = (Bee)gameMap.getGameObjectFromCoords(beeLocation);
            currentBee.updateLifetime(delta);
            if(currentBee.isDead()){
                gameMap.removeGameObject(beeLocation);
            }
        }
    }

    /** The pre-game check: is there at least two bees and one hive? */
    private void preGameCheck(GameMap gtp){

        //Check the requirement
        if(gtp.getBeeCount() < 2 || gtp.getHiveCount() < 1)
            throw new IllegalGameStart(gtp.getBeeCount(), gtp.getHiveCount()); //TODO this is how the game ends. Handle this differently.
    }

    /** Queen rule check: if there is two or more bees near a hive and
     *  there is no queen on the way or present at hive. Then create one. */
    private void queenRuleCheck(GameMap gtp){

        //Check hives for queen. If no = spawn queen.
        for(Vector2 hive : gtp.getHiveLocations()){
            if(checkSurroundings(new Queen(), hive) == 0){ //Check if there is no queen around the hive
                if(checkSurroundings(new Bee(0,0), hive) >= 2){ //Check if there is at least two bees near hive
                    Vector2 emptyCell = findEmptyCell(hive);  //Find empty cell
                    gameMap.addGameObject(emptyCell, new Queen()); //Spawn queen in empty cell
                }
            }
        }
    }

    /** Harvest rule check: if the hive contains a queen, then the bee will choose a psudo random direction and
     *  start searching for a flower. When found the bee will harvest it, and return to the hive*/
    private void harvestRuleCheck(GameMap gtp){

        ArrayList<Vector2> hiveLocations = gtp.getHiveLocations();
        ArrayList<Vector2> flowerLocations = gtp.getFlowerLocations();
        ArrayList<Vector2> beeLocations = gtp.getBeeLocations(); //TODO BUG
        //ArrayList<Vector2> beeLocations = getGameObjectLocations(new Bee(0,0)); //TODO works. Can replace above line


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
                    gameMap.addGameObject(emptyLocation, new Bee(hiveCoords));

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

    /**
     *  @param beesLocations an arrayList containing all bee locations.
     *  @param flowerLocations an arrayList containing all flower locations. */
    private void moveBees(ArrayList<Vector2> beesLocations, ArrayList<Vector2> flowerLocations){

        for(Vector2 beeLocation : beesLocations){

            Bee currentBee = (Bee)gameMap.getGameObjectFromCoords(beeLocation);

            if(currentBee.hasPollen()){ //If bee has pollen. Then GoTowardsHive

                //Get sorted linkedList of next coordinates and all eight directions.
                LinkedList<Vector2> coordinates = getSortedSetOfDirectionalCoordinates(currentBee.getHiveCoordinates(), beeLocation);

                //Check all 8 directions and take the first available one (first empty cell)
                for(int i = 0; i < 7; i++){

                    Vector2 coordinate = coordinates.get(i);

                    if(gameMap.moveGameObject(beeLocation, coordinate))
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

                    if(gameMap.moveGameObject(beeLocation, coordinate))
                        break;
                }
            }
        }
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
        //TODO BUG!!! x and y SHOULD BE SWITCHED!!!
        //TODO should this not be replaced with "instance of"????
        if(gameMap.getGameObjectFromCoords(y+1, x).getClass() == Bee.class)
            bees.add((Bee)gameMap.getGameObjectFromCoords(y+1, x));
        if(gameMap.getGameObjectFromCoords(y+1, x+1).getClass() == Bee.class)
            bees.add((Bee)gameMap.getGameObjectFromCoords(y+1, x+1));
        if(gameMap.getGameObjectFromCoords(y, x+1).getClass() == Bee.class)
            bees.add((Bee)gameMap.getGameObjectFromCoords(y, x+1));
        if(gameMap.getGameObjectFromCoords(y-1, x+1).getClass() == Bee.class)
            bees.add((Bee)gameMap.getGameObjectFromCoords(y-1, x-1));
        if(gameMap.getGameObjectFromCoords(y-1, x).getClass() == Bee.class)
            bees.add((Bee)gameMap.getGameObjectFromCoords(y-1, x));
        if(gameMap.getGameObjectFromCoords(y-1, x-1).getClass() == Bee.class)
            bees.add((Bee)gameMap.getGameObjectFromCoords(y-1, x));
        if(gameMap.getGameObjectFromCoords(y, x-1).getClass() == Bee.class)
            bees.add((Bee)gameMap.getGameObjectFromCoords(y, x-1));
        if(gameMap.getGameObjectFromCoords(y+1, x-1).getClass() == Bee.class)
            bees.add((Bee)gameMap.getGameObjectFromCoords(y+1, x-1));

        return bees;
    }

    /** Searches the map and returns the coordinates for the requested game object. */
    private ArrayList<Vector2> getGameObjectLocations(GameObject gameObject){

        ArrayList<Vector2> gameObjectLocations = new ArrayList<Vector2>();

        //Go through map and add all requested GameObject-locations to the array
        for(int y = 0; y < gameMap.getMapHeight(); y++){
            for(int x = 0; x < gameMap.getMapWidth(); x++){

                //TODO can be replaced with "instance of"???
                if(gameMap.getGameObjectFromCoords(new Vector2(x, y)).getClass() == gameObject.getClass())
                    gameObjectLocations.add(new Vector2(x, y));
            }
        }

        return gameObjectLocations;
    }

    /** @return number of requestedGameObject surrounding given coordinates. */
    private int checkSurroundings(GameObject requestedGameObject, Vector2 coords){

        ArrayList<GameObject> surroundingCells = new ArrayList<GameObject>();
        int x = coords.x;
        int y = coords.y;

        //Get all surrounding cells
        surroundingCells.add(gameMap.getGameObjectFromCoords(x, y+1));
        surroundingCells.add(gameMap.getGameObjectFromCoords(x + 1,y + 1));
        surroundingCells.add(gameMap.getGameObjectFromCoords(x + 1, y));
        surroundingCells.add(gameMap.getGameObjectFromCoords(x + 1, y - 1));
        surroundingCells.add(gameMap.getGameObjectFromCoords(x, y - 1));
        surroundingCells.add(gameMap.getGameObjectFromCoords(x - 1, y - 1));
        surroundingCells.add(gameMap.getGameObjectFromCoords(x - 1, y));
        surroundingCells.add(gameMap.getGameObjectFromCoords(x - 1, y + 1));

        int counter = 0;

        //Count surrounding cells containing requested gameObject
        for(GameObject gameObject : surroundingCells)
            if(gameObject.getClass() == requestedGameObject.getClass()) //TODO "instance of" ??
                counter++;

        return counter;
    }
    /** Searches for an empty cell near given coordinates, returns coordinates of empty cell. */
    private Vector2 findEmptyCell(Vector2 coords){

        int x = coords.x;
        int y = coords.y;
        int layer = 1;

        while(true) {

            if(gameMap.getGameObjectFromCoords(x, y + layer) instanceof GameStructure)
                if(!((GameStructure)gameMap.getGameObjectFromCoords(x, y + layer)).isSolid())
                    return new Vector2(x, y + layer);


            if (gameMap.getGameObjectFromCoords(x + layer, y + layer) instanceof GameStructure)
                if(!((GameStructure)gameMap.getGameObjectFromCoords(x + layer, y + layer)).isSolid())
                    return new Vector2(x + layer, y + layer);

            if (gameMap.getGameObjectFromCoords(x + layer, y) instanceof GameStructure)
                if(!((GameStructure)gameMap.getGameObjectFromCoords(x + layer, y)).isSolid())
                    return new Vector2(x + layer, y);

            if (gameMap.getGameObjectFromCoords(x + layer, y - layer) instanceof GameStructure)
                if(!((GameStructure)gameMap.getGameObjectFromCoords(x + layer, y - layer)).isSolid())
                    return new Vector2(x + layer, y - layer);

            if (gameMap.getGameObjectFromCoords(x, y - layer) instanceof GameStructure)
                if(!((GameStructure)gameMap.getGameObjectFromCoords(x, y - layer)).isSolid())
                    return new Vector2(x, y - layer);

            if (gameMap.getGameObjectFromCoords(x - layer, y - layer) instanceof GameStructure)
                if(!((GameStructure)gameMap.getGameObjectFromCoords(x - layer, y - layer)).isSolid())
                    return new Vector2(x - layer, y - layer);

            if (gameMap.getGameObjectFromCoords(x - layer, y) instanceof GameStructure)
                if(!((GameStructure)gameMap.getGameObjectFromCoords(x - layer, y)).isSolid())
                    return new Vector2(x - layer, y);

            if (gameMap.getGameObjectFromCoords(x - layer, y + layer) instanceof GameStructure)
                if(!((GameStructure)gameMap.getGameObjectFromCoords(x - layer, y + layer)).isSolid())
                    return new Vector2(x - layer, y + layer);

            layer++;
        }
    }

    public GameMap getGameMap() {
        return gameMap;
    }
}
