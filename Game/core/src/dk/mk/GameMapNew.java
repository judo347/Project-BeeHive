package dk.mk;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.mk.gameObjects.*;

import java.util.*;


//TODO Maybe use own defines int[].. so it only and always have two slots for coordinates

public class GameMapNew {

    public enum Direction{
        NORTH(0, 1, 0), NORTHEAST(1, 1, 1), EAST(1, 0, 2), SOUTHEAST(1, -1, 3), SOUTH(0, -1, 4), SOUTHWEST(-1, -1, 5), WEST(-1, 0, 6), NORTHWEST(-1, 1, 7);

        private int x;
        private int y;

        private int transformationValue; //Take the default list times this number to get the desired list.

        Direction(int x, int y, int transformationValue) {
            this.x = x;
            this.y = y;
            this.transformationValue = transformationValue;
        }

        public int[] getDirectionArray(){
            return new int[]{x, y};
        }

        public int getTransformationValue(){
            return this.transformationValue;
        }

        public static LinkedList<Direction> getLinkedList(){
            return new LinkedList<Direction>(Arrays.asList(Direction.values()));
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
        moveBees(beeLocations);

    }

    private void moveBees(ArrayList<int[]> beesLocations){

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

                for(int i = 0; i < 8; i++){

                    int[] coordinate = coordinates.get(i);

                    if(moveBee(new int[]{beeLocation[0], beeLocation[1]}, new int[]{coordinate[0], coordinate[1]}))
                        break;

                }
            }else{ //GoAwayFromHive




            }
        }
    }

    /** Moves the bee. Return true if bee was moved. */
    private boolean moveBee(int[] myCoords, int[] destCoords){

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

        return false;
    }

    /** */
    private LinkedList<int[]> getSortedSetOfDirectionalCoordinates(int destX, int destY, int myX, int myY){

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
