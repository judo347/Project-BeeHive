package dk.mk.bee.game.objects.beespecies.scenarios;

import dk.mk.bee.game.objects.beespecies.template.Bee;
import dk.mk.bee.game.objects.beespecies.template.Flower;
import dk.mk.bee.game.objects.beespecies.template.Hive;
import dk.mk.bee.game.objects.beespecies.template.Queen;
import dk.mk.bee.game.util.Vector2;

/** Template for scenario providers. A provider is the only link between game map/world and the game objects */
public abstract class ScenarioProvider {
    public abstract Bee getNewBee(Hive hive, Vector2 location);
    public abstract Hive getNewHive();
    public abstract Flower getNewFlower();
    public abstract Queen getNewQueen();
}
