package dk.mk;

import dk.mk.gameObjects.Bee;
import dk.mk.gameObjects.Flower;
import dk.mk.gameObjects.GameStructure;
import dk.mk.gameObjects.Hive;
import org.junit.Assert;
import org.junit.Test;

public class GameMapTest {

    @Test
    public void counterTest01(){
        GameClass gameClass = new GameClass(SpawnMethods.SPAWN_TYPE.TESTCOUNTER);
        GameMap gameMap = gameClass.getGameMap();

        Assert.assertEquals(5, gameMap.getBeeCount());
        Assert.assertEquals(5, gameMap.getHiveCount());
        Assert.assertEquals(5, gameMap.getFlowerCount());
    }

    @Test
    public void hiveLocationTypeTest01(){
        GameClass gameClass = new GameClass(SpawnMethods.SPAWN_TYPE.TESTCOUNTER);
        GameMap gameMap = gameClass.getGameMap();

        for (Vector2 hiveLocation : gameMap.getHiveLocations()) {
            Assert.assertTrue(gameClass.getGameMap().getGameObjectFromCoords(hiveLocation.x, hiveLocation.y) instanceof Hive);
        }
    }

    @Test
    public void flowerLocationTypeTest01(){
        GameClass gameClass = new GameClass(SpawnMethods.SPAWN_TYPE.TESTCOUNTER);
        GameMap gameMap = gameClass.getGameMap();

        for (Vector2 flowerLocation : gameMap.getFlowerLocations()) {
            Assert.assertTrue(gameClass.getGameMap().getGameObjectFromCoords(flowerLocation.x, flowerLocation.y) instanceof Flower);
        }
    }

    @Test
    public void beeLocationTypeTest01(){
        GameClass gameClass = new GameClass(SpawnMethods.SPAWN_TYPE.TESTCOUNTER);
        GameMap gameMap = gameClass.getGameMap();

        for (Vector2 beeLocation : gameMap.getBeeLocations()) {
            Assert.assertTrue(gameClass.getGameMap().getGameObjectFromCoords(beeLocation.x, beeLocation.y) instanceof Bee);
        }
    }

    @Test
    public void addGameObjectTest01(){ //SIMPLE HIVE ADD
        GameClass gameClass = new GameClass(SpawnMethods.SPAWN_TYPE.TESTCOUNTER);
        GameMap gameMap = gameClass.getGameMap();

        Vector2 newHiveLocation = new Vector2(10,10);
        Hive newHive = new Hive();

        boolean added = gameMap.addGameObject(newHiveLocation, newHive);

        Assert.assertTrue(added);

        //Check the coords
        Assert.assertTrue(gameMap.getGameObjectFromCoords(newHiveLocation).getClass() == newHive.getClass());

        //Check the count
        Assert.assertEquals(6, gameMap.getHiveCount());

    }

    @Test
    public void addGameObjectTest02(){ //SIMPLE BEE ADD
        GameClass gameClass = new GameClass(SpawnMethods.SPAWN_TYPE.TESTCOUNTER);
        GameMap gameMap = gameClass.getGameMap();

        Vector2 newBeeLocation = new Vector2(10,10);
        Bee newBee = new Bee(0, 0);

        boolean added = gameMap.addGameObject(newBeeLocation, newBee);

        Assert.assertTrue(added);

        //Check the coords
        Assert.assertTrue(gameMap.getGameObjectFromCoords(newBeeLocation).getClass() == newBee.getClass());

        //Check the count
        Assert.assertEquals(6, gameMap.getBeeCount());

    }

    @Test
    public void addGameObjectTest03(){ //SIMPLE FLOWER ADD
        GameClass gameClass = new GameClass(SpawnMethods.SPAWN_TYPE.TESTCOUNTER);
        GameMap gameMap = gameClass.getGameMap();

        Vector2 newFlowerLocation = new Vector2(10,10);
        Flower newFlower = new Flower();

        boolean added = gameMap.addGameObject(newFlowerLocation, newFlower);

        Assert.assertTrue(added);

        //Check the coords
        Assert.assertTrue(gameMap.getGameObjectFromCoords(newFlowerLocation).getClass() == newFlower.getClass());

        //Check the count
        Assert.assertEquals(6, gameMap.getFlowerCount());

    }

    @Test
    public void addGameObjectTest04(){ //ADD OBJECT ON BORDER
        GameClass gameClass = new GameClass(SpawnMethods.SPAWN_TYPE.TESTCOUNTER);
        GameMap gameMap = gameClass.getGameMap();

        Vector2 newFlowerLocation = new Vector2(0,0);
        Flower newFlower = new Flower();

        boolean added = gameMap.addGameObject(newFlowerLocation, newFlower);

        Assert.assertFalse(added);

        //Check the coords
        Assert.assertFalse(gameMap.getGameObjectFromCoords(newFlowerLocation).getClass() == newFlower.getClass());

        //Check the count
        Assert.assertEquals(5, gameMap.getFlowerCount());

    }

    @Test
    public void addGameObjectTest05(){ //ADD OBJECT OUT OF BOUNDS
        GameClass gameClass = new GameClass(SpawnMethods.SPAWN_TYPE.TESTCOUNTER);
        GameMap gameMap = gameClass.getGameMap();

        Vector2 newFlowerLocation = new Vector2(gameMap.getMapWidth() -1,1);
        Flower newFlower = new Flower();

        boolean added = gameMap.addGameObject(newFlowerLocation, newFlower);

        Assert.assertFalse(added);

        //Check the coords
        Assert.assertFalse(gameMap.getGameObjectFromCoords(newFlowerLocation).getClass() == newFlower.getClass());

        //Check the count
        Assert.assertEquals(5, gameMap.getFlowerCount());

    }

    @Test
    public void borderTest01(){
        GameClass gameClass = new GameClass(SpawnMethods.SPAWN_TYPE.TESTCOUNTER);
        GameMap gameMap = gameClass.getGameMap();

        GameStructure topLeft = (GameStructure)gameMap.getGameObjectFromCoords(0,0);
        GameStructure topRight = (GameStructure)gameMap.getGameObjectFromCoords(gameMap.getMapWidth() - 1, 0);
        GameStructure bottomLeft = (GameStructure)gameMap.getGameObjectFromCoords(0, gameMap.getMapHeight() - 1);
        GameStructure bottomRight = (GameStructure)gameMap.getGameObjectFromCoords(gameMap.getMapWidth() - 1, gameMap.getMapHeight() - 1);

        Assert.assertTrue(topLeft.isSolid());
        Assert.assertTrue(topRight.isSolid());
        Assert.assertTrue(bottomLeft.isSolid());
        Assert.assertTrue(bottomRight.isSolid());

    }
}
