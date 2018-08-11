package dk.mk;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.mk.gameObjects.*;

import java.util.*;


//TODO Maybe use own defines int[].. so it only and always have two slots for coordinates

//TODO bee should "Bouce" on walls. MIssing feature: new queen find new place to live.

public class GameMapNew {

    public enum Direction{
        NORTH(0, 1), NORTHEAST(1, 1), EAST(1, 0), SOUTHEAST(1, -1), SOUTH(0, -1), SOUTHWEST(-1, -1), WEST(-1, 0), NORTHWEST(-1, 1);

        private int x;
        private int y;

        Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int[] getDirectionArray(){
            return new int[]{x, y};
        }

        public static LinkedList<Direction> getLinkedList(){
            return new LinkedList<Direction>(Arrays.asList(Direction.values()));
        }

        public Direction findOppositeDirection(){

            for(Direction newDirection : Direction.values()){
                if((newDirection.x * -1) == this.x && (newDirection.y * -1) == this.y)
                    return newDirection;
            }

            return null; //Should not get here
        }
    }

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
        map[24][25] = new Bee(25, 25);
        map[26][25] = new Bee(25,25);
        ((Bee)map[26][25]).givePollen();


        //Hive two
        map[70][70] = new Hive();
        map[71][70] = new Bee(70,70);
        map[71][71] = new Bee(70,70);
        map[70][71] = new Bee(70,70);

        //Flowers
        map[4][4] = new Flower();
        map[5][4] = new Flower();
        map[12][30] = new Flower();
        map[6][23] = new Flower();
        map[22][4] = new Flower();
        map[32][32] = new Flower();
        map[74][32] = new Flower();
        map[32][74] = new Flower();
        map[32][75] = new Bee(70,70);
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
        harvestRuleCheck();



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

        ArrayList<int[]> hives = new ArrayList<int[]>();
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

                if(checkSurroundings(new Bee(0,0), hive[0], hive[1]) >= 2){ //Check if there is two bees near hive

                    //System.out.println("There are at least two bees!");

                    int[] emptyCell = findEmptyCell(hive[0], hive[1]);  //Spawn queen on empty spot
                    map[emptyCell[1]][emptyCell[0]] = new Queen();
                }
            }
        }
    }

    /** Checks if a bee near a hive has pollen. If true remove pollen and spawn new bee. */
    private void isBeeWithPollenNearHiveEqualsSpawn(ArrayList<int[]> hiveLocations){

        for(int[] coords : hiveLocations){
            ArrayList<Bee> beesAroundHive = getBeesAroundGameObject(new Hive(), coords[0], coords[1]);

            //System.out.println(beesAroundHive.size());

            for(Bee bee : beesAroundHive){
                if(bee.hasPollen()){
                    //Spawn new bee at hive
                    int[] emptyLocation = findEmptyCell(coords[0], coords[1]);
                    map[emptyLocation[1]][emptyLocation[0]] = new Bee(coords[0], coords[1]);

                    //Remove pollen from bee
                    bee.removePollen();
                }
            }

        }
    }

    /** Checks if a bee near a flower. If true give pollen to bee. */
    private void isBeeWithPollenNearFlowerGivePollen(ArrayList<int[]> flowerLocations){

        for(int[] coords : flowerLocations){
            ArrayList<Bee> beesAroundFlower = getBeesAroundGameObject(new Bee(0,0), coords[0], coords[1]);

            for(Bee bee : beesAroundFlower){
                bee.givePollen();
            }
        }
    }

    //TODO Optimize so that the map has a list of the hives and such..
    private void harvestRuleCheck(){

        //Harvest rule: If the hive contains a queen, then the bee will choose a random direction and
        //start searching for a flower. When found the bee will harvest it, and return to the hive.

        //If contains queen, fly in psudo random direction

        ArrayList<int[]> hiveLocations = getGameObjectLocations(new Hive()); //Find hive locations
        ArrayList<int[]> flowerLocations = getGameObjectLocations(new Flower()); //Find flowers
        ArrayList<int[]> beeLocations = getGameObjectLocations(new Bee(0,0));

        //Does bees near hive have pollen? then spawn new bee
        isBeeWithPollenNearHiveEqualsSpawn(hiveLocations);

        //Is bee near flower? Give pollen
        isBeeWithPollenNearFlowerGivePollen(flowerLocations);


        //AT THIS POINT: BEES HAVE GIVEN OR RECEIVED POLLEN; ITS NOW TIME TO MOVE;
        moveBees(beeLocations, flowerLocations);

    }

    private void moveBees(ArrayList<int[]> beesLocations, ArrayList<int[]> flowerLocations){

        for(int[] beeLocation : beesLocations){

            Bee currentBee = (Bee)map[beeLocation[1]][beeLocation[0]];

            if(currentBee.hasPollen()){ //GoTowardsHive

                LinkedList<int[]> coordinates = getSortedSetOfDirectionalCoordinates(currentBee.getHiveX(), currentBee.getHiveY(), beeLocation[0], beeLocation[1]); //Get sorted set of next coordinates

                //System.out.println(beeLocation[0] + " " + beeLocation[1]);
                //System.out.println(currentBee.getHiveX() + " " + currentBee.getHiveY());
                //System.out.println("");

                //for(int[] ints : coordinates){
                //    System.out.println(ints[0] + " " + ints[1]);
                //}

                for(int i = 0; i < 7; i++){

                    int[] coordinate = coordinates.get(i);

                    if(moveBee(new int[]{beeLocation[0], beeLocation[1]}, new int[]{coordinate[0], coordinate[1]}))
                        break;

                }
            }else{ //GoAwayFromHive

                //currentBee;
                //beeLocation;

                //If the bees currentHuntDirection is not set. Give it one
                if(currentBee.getCurrentHuntDirection() == null)
                    currentBee.setCurrentHuntDirection(findDirectionFromCoords(currentBee.getHiveX(), currentBee.getHiveY(), beeLocation[0], beeLocation[1]).findOppositeDirection());//Calculate the direction away from hive, give that to the bee

                //If bee is # moves from flower, set direction to that way //TODO Might need to be set to a higher number
                int[] nearestFlower = isBeeNearFlower(beeLocation, flowerLocations, Bee.BEE_FLOWER_ALERT_DIST);

                if(nearestFlower != null)
                    currentBee.setCurrentHuntDirection(findDirectionFromCoords(nearestFlower[0], nearestFlower[1], beeLocation[0], beeLocation[1])); //Get direction of flower, and set that to the be the bees direction

                //move in that direction if possible, or continues on list
                //Get list of directions in bees desired direction
                LinkedList<int[]> coordinates = getSortedSetOfDirectionalCoordinates(beeLocation[0] + currentBee.getCurrentHuntDirection().x, beeLocation[1] + currentBee.getCurrentHuntDirection().y, beeLocation[0], beeLocation[1]);

                for(int i = 0; i < 7; i++){

                    int[] coordinate = coordinates.get(i);

                    if(moveBee(new int[]{beeLocation[0], beeLocation[1]}, new int[]{coordinate[0], coordinate[1]}))
                        break;

                }
            }
        }
    }

    /** Find the coordinates of the closest flower.
     * @param beeLocation the coordinates of the bee
     * @param flowersLocations ArrayList with coordinates of all flowers
     * @param maxDist max allowed dist to flowers
     * @return finds the closest flower within reach (maxDist), or null if non is found. */ //TODO MIGHT CONTAIN BUGS!!
    private int[] isBeeNearFlower(int[] beeLocation, ArrayList<int[]> flowersLocations, int maxDist){

        ArrayList<double[]> inReachFlowers = new ArrayList<double[]>();

        for(int[] ints : flowersLocations){

            //Calculate distance from bee to flower
            int xDiff = Math.abs(ints[0] - beeLocation[0]);
            int yDiff = Math.abs(ints[1] - beeLocation[1]);

            //Create list of flowers within reach, less or equal to maxDist
            //Get list of flowers in reach
            if(xDiff <= maxDist && yDiff <= maxDist){
                double distance = Math.sqrt(Math.pow(ints[0], 2) + Math.pow(ints[1], 2)); //Calculate distance in double
                inReachFlowers.add(new double[]{ints[0], ints[1], distance}); //Save the distance with the valid flower
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


            return new int[]{(int)closestFlower[0], (int)closestFlower[1]};
        }

        return null;
    }

    /** Moves the bee. Return true if bee was moved. */
    private boolean moveBee(int[] myCoords, int[] destCoords){

        try{
            if(map[destCoords[1]][destCoords[0]] instanceof GameStructure){
                if(!((GameStructure) map[destCoords[1]][destCoords[0]]).isSolid()){

                    //MOVE
                    map[destCoords[1]][destCoords[0]] = new Bee(((Bee)map[myCoords[1]][myCoords[0]]).getHiveX(), ((Bee)map[myCoords[1]][myCoords[0]]).getHiveY());
                    if(((Bee)map[myCoords[1]][myCoords[0]]).hasPollen()) //Keep pollen state
                        ((Bee)map[destCoords[1]][destCoords[0]]).givePollen();
                    map[myCoords[1]][myCoords[0]] = new GameStructure(false); //Delete old bee

                    return true;
                }
            }
        }catch (ArrayIndexOutOfBoundsException e){
            return false;
        }

        return false;
    }

    /** Finds a direction from myCoords to destCoords. */
    private Direction findDirectionFromCoords(int destX, int destY, int myX, int myY){

        int xDifference =  destX - myX;
        int yDifference =  destY - myY;

        xDifference = (xDifference == 0) ? 0 : (xDifference < 0) ? -1 : 1; //Reduce to -1, 0, 1
        yDifference = (yDifference == 0) ? 0 : (yDifference < 0) ? -1 : 1; //Reduce to -1, 0, 1

        Direction firstDirection = null;

        //Find the first direction
        for(Direction direction : Direction.values()){
            if(direction.x == xDifference && direction.y == yDifference)
                firstDirection = direction;
        }

        return firstDirection;
    }

    /** */
    private LinkedList<int[]> getSortedSetOfDirectionalCoordinates(int destX, int destY, int myX, int myY){

        //Find the first direction
        Direction firstDirection = findDirectionFromCoords(destX, destY, myX, myY);

        LinkedList<Direction> directionLinkedList = Direction.getLinkedList();

        //Cycles through the list untill firstDirection is first in list
        while(firstDirection != directionLinkedList.peek()){
            directionLinkedList.addLast(directionLinkedList.poll());
        }

        //Convert list to have the right order
        LinkedList<Direction> directionLinkedListOrdered = new LinkedList<Direction>();

        while(directionLinkedList.size() > 0){
            directionLinkedListOrdered.add(directionLinkedList.pollFirst());
            directionLinkedListOrdered.add(directionLinkedList.pollLast());
        }

        return convertRelativeToMyPos(directionLinkedListOrdered, myX, myY);
    }

    private LinkedList<int[]> convertRelativeToMyPos(LinkedList<Direction> list, int myX, int myY){

        LinkedList<int[]> listInt = new LinkedList<int[]>();

        for (Direction direction : list) {
            listInt.addLast(direction.getDirectionArray());
        }

        for (int[] ints : listInt) {
            ints[0] = ints[0] + myX;
            ints[1] = ints[1] + myY;
        }

        return listInt;
    }

    private ArrayList<Bee> getBeesAroundGameObject(GameObject gameObject, int x, int y){

        ArrayList<Bee> bees = new ArrayList<Bee>();

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

        //System.out.println((gameObject.getClass() == Hive.class) ? " bees near hive: " + bees.size() : " bees near flower: " + bees.size());

        return bees;
    }

    /** Searches the map and returns the coordinates for the requested game object. */
    private ArrayList<int[]> getGameObjectLocations(GameObject gameObject){

        ArrayList<int[]> gameObjectLocations = new ArrayList<int[]>();

        for(int y = 0; y < mapHeight; y++){
            for(int x = 0; x < mapWidth; x++){

                if(map[y][x].getClass() == gameObject.getClass())
                    gameObjectLocations.add(new int[]{x, y});
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
