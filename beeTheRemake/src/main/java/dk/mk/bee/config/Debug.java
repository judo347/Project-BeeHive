package dk.mk.bee.config;

import dk.mk.bee.game.objects.GameObject;
import dk.mk.bee.game.objects.beespecies.template.tile.Tile;

public class Debug {
    public static void debugLogGameObject(GameObject gameObject) {
        if(gameObject instanceof Tile) {
            switch (((Tile)gameObject).getType()) {
                case BORDER -> Logger.debug("B");
                case WHITESPACE -> Logger.debug("W");
                default -> Logger.debug("-");
            }
        } else {
            Logger.debug("+");
        }
    }
}
