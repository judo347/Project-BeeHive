package dk.mk;

import dk.mk.gameObjects.Hive;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class GameTickPacketTest {

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
        GameMap gameMap = new GameMap(SpawnMethods.SPAWN_TYPE.TESTCOUNTER);
        GameTickPacket gtp = new GameTickPacket(gameMap.getMap());

        Assert.assertEquals(5, gtp.getBeeCount());
        Assert.assertEquals(5, gtp.getHiveCount());
        Assert.assertEquals(5, gtp.getFlowerCount());
    }

    @Test
    public void hiveLocationTypeTest01(){
        GameMap gameMap = new GameMap(SpawnMethods.SPAWN_TYPE.TESTCOUNTER);
        GameTickPacket gtp = new GameTickPacket(gameMap.getMap());

        for (Vector2 hiveLocation : gtp.getHiveLocations()) {
            Assert.assertTrue(gameMap.getMap()[hiveLocation.x][hiveLocation.y] instanceof Hive);
        }
    }
}
