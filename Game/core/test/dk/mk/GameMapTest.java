package dk.mk;

import dk.mk.gameObjects.Bee;
import dk.mk.gameObjects.Flower;
import dk.mk.gameObjects.Hive;
import org.junit.Assert;
import org.junit.Test;

public class GameMapTest {

    /**
     * @Test
     *     public void testRoundRobinBracket01() {
     *
     *         int numberOfTeams = 4;
     *         int teamSize = 1;
     *
     *         RoundRobinFormat bracket = new RoundRobinFormat();
     *         bracket.start(generateTeams(numberOfTeams, teamSize));
     *
     *         assertEquals(bracket.getStatus(), StageStatus.RUNNING);
     *     }
     */

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
}
