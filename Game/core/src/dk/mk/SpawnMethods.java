package dk.mk;

import dk.mk.gameObjects.Bee;
import dk.mk.gameObjects.Flower;
import dk.mk.gameObjects.GameObject;
import dk.mk.gameObjects.Hive;

/** The class contains different methods used to start different times of games. */
public class SpawnMethods {

    public enum SPAWN_TYPE {
        TESTSPAWN, TESTGIFRULES, TESTCOUNTER;
    }

    public SpawnMethods(SPAWN_TYPE spawn_type, GameObject[][] map) {

        if(spawn_type == SPAWN_TYPE.TESTSPAWN)
            testSpawn(map);
        else if (spawn_type == SPAWN_TYPE.TESTGIFRULES)
            testGIFRules(map);
        else if (spawn_type == SPAWN_TYPE.TESTCOUNTER)
            spawnTestCounter(map);
        else
            throw new IllegalArgumentException();
    }

    private void testSpawn(GameObject[][] map){

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
        map[32][75] = new Bee(70,70);

        //Flowers
        map[4][4] = new Flower();
        map[5][4] = new Flower();
        map[12][30] = new Flower();
        map[6][23] = new Flower();
        map[22][4] = new Flower();
        map[32][32] = new Flower();
        map[74][32] = new Flower();
        map[32][74] = new Flower();
    }

    private void testGIFRules(GameObject[][] map){

        map[5][5] = new Hive();
        map[4][6] = new Bee(2, 1);
        ((Bee)map[4][6]).givePollen();
        map[4][4] = new Bee(1,2);
        map[10][10] = new Flower();

    }

    private void spawnTestCounter(GameObject[][] map){

        map[25][25] = new Hive();
        map[70][70] = new Hive();
        map[26][25] = new Hive();
        map[33][33] = new Hive();
        map[36][33] = new Hive();

        map[24][25] = new Bee(25, 25);
        map[71][70] = new Bee(70,70);
        map[71][71] = new Bee(70,70);
        map[70][71] = new Bee(70,70);
        map[70][73] = new Bee(70,70);

        map[4][4] = new Flower();
        map[5][4] = new Flower();
        map[12][30] = new Flower();
        map[6][23] = new Flower();
        map[22][4] = new Flower();
    }
}
