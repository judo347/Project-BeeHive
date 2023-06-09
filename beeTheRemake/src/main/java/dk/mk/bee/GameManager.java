package dk.mk.bee;

import dk.mk.bee.config.ApplicationConfig;
import dk.mk.bee.config.Logger;
import dk.mk.bee.game.config.GameInfo;
import dk.mk.bee.game.map.GameMap;
import dk.mk.bee.ui.config.VisualInfo;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


/** Should be the only link between visuals and the backend game */
public class GameManager {

    private GameMap gameMap;

    public void  setupGame() {
        this.gameMap = new GameMap();
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public void render(GraphicsContext border, GraphicsContext whitespace) {
        border.setFill(Color.rgb(78, 86, 92));
        Logger.debugLn("Printing game map:");
        for(int y = 0; y < gameMap.height(); y++){
            for(int x = 0; x < gameMap.width(); x++){
                Logger.debug(String.valueOf(gameMap.map()[y][x].getType().name().charAt(0)));

                switch (gameMap.map()[y][x].getType()) {
                    case BORDER -> border.fillRect(x * VisualInfo.SQUARE_SIZE(), y * VisualInfo.SQUARE_SIZE(), VisualInfo.SQUARE_SIZE(), VisualInfo.SQUARE_SIZE());
                }
            }
            Logger.debugLn();
        }
    }
}
