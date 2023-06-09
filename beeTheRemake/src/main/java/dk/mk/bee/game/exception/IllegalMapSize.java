package dk.mk.bee.game.exception;

import dk.mk.bee.ui.config.VisualInfo;

public class IllegalMapSize extends RuntimeException {
    public IllegalMapSize() {
        super("THE MAP SIZE IS NOT % 10. WIDTH: " + VisualInfo.WINDOW_WIDTH() + " HEIGHT: " + VisualInfo.WINDOW_HEIGHT() + ".");
    }
}
