package dk.mk.bee.game.objects.beespecies.scenarios.meadow;

import dk.mk.bee.game.objects.beespecies.scenarios.ScenarioProvider;
import dk.mk.bee.game.objects.beespecies.template.Bee;
import dk.mk.bee.game.objects.beespecies.template.Flower;
import dk.mk.bee.game.objects.beespecies.template.Hive;
import dk.mk.bee.game.objects.beespecies.template.Queen;
import dk.mk.bee.game.objects.beespecies.template.type.BeeType;
import dk.mk.bee.game.objects.beespecies.template.type.FlowerType;
import dk.mk.bee.game.objects.beespecies.template.type.HiveType;
import dk.mk.bee.game.objects.beespecies.template.type.QueenType;
import dk.mk.bee.game.util.Vector2;

public class MeadowProvider extends ScenarioProvider {

    //TODO below settings could be loaded from config file
    private final float beeLifespan = 120;
    private final Float queenLifespan = null;

    @Override
    public Bee getNewBee(Hive hive, Vector2 location) {
        return new Bee(BeeType.DEFAULT, beeLifespan, hive, new Vector2(location.x(), location.y()));
    }

    @Override
    public Hive getNewHive() {
        return new Hive(HiveType.DEFAULT);
    }

    @Override
    public Flower getNewFlower() {
        return new Flower(FlowerType.DEFAULT);
    }

    @Override
    public Queen getNewQueen() {
        return new Queen(QueenType.DEFAULT, queenLifespan);
    }
}
