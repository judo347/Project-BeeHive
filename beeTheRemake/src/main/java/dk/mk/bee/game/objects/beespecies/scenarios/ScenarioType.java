package dk.mk.bee.game.objects.beespecies.scenarios;

import dk.mk.bee.game.objects.beespecies.scenarios.ScenarioProvider;
import dk.mk.bee.game.objects.beespecies.scenarios.meadow.MeadowProvider;

public enum ScenarioType {
    DEFAULT(new MeadowProvider()); //TODO correct?

    public final ScenarioProvider scenarioProvider;

    ScenarioType(ScenarioProvider scenarioProvider) {
        this.scenarioProvider = scenarioProvider;
    }
}
