package dk.mk;

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
        GameMap gtp = new GameMap(gameClass.getMap());

        Assert.assertEquals(5, gtp.getBeeCount());
        Assert.assertEquals(5, gtp.getHiveCount());
        Assert.assertEquals(5, gtp.getFlowerCount());
    }

    @Test
    public void hiveLocationTypeTest01(){
        GameClass gameClass = new GameClass(SpawnMethods.SPAWN_TYPE.TESTCOUNTER);
        GameMap gtp = new GameMap(gameClass.getMap());

        for (Vector2 hiveLocation : gtp.getHiveLocations()) {
            Assert.assertTrue(gameClass.getMap()[hiveLocation.x][hiveLocation.y] instanceof Hive);
        }
    }
}
