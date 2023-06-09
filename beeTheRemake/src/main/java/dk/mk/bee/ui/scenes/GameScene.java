package dk.mk.bee.ui.scenes;

import dk.mk.bee.GameManager;
import dk.mk.bee.ui.config.VisualInfo;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class GameScene { //TODO rename to content or something?

    private Canvas canvas;
    private long lastTickTime;
    private GraphicsContext gc;

    // Game state variables //TODO move or remove
    private int x;
    private int y;

    private GameManager gameManager = new GameManager();



    private StackPane content;
    private Button buttonToMainMenuNavigation;

    public GameScene() {
        this.content = sceneContent();
        this.gameManager.setupGame();
    }

    public void renderStaticContent() {
        //TODO content that stays the same. Keep in seperate canvas and re-apply after clear?
    }

    public void gameLoop() { //TODO maybe extract to other class?

        lastTickTime = System.currentTimeMillis();
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long currentTickTime = System.currentTimeMillis();
                if (currentTickTime - lastTickTime >= VisualInfo.TICK_INTERVAL) {
                    updateGame(); // Update game state
                    renderGame(); // Render game state
                    lastTickTime = currentTickTime;
                }
            }
        };
        gameLoop.start();
    }

    private void updateGame() {
        // Update game logic based on ticks
        // For example, move objects, check for collisions, etc.
        x++; // Move the object to the right
    }

    private void renderGame() {
        // Clear the canvas
        gc.clearRect(0, 0, VisualInfo.WINDOW_WIDTH(), VisualInfo.WINDOW_HEIGHT());

        // Render the game objects
        //gc.fillOval(x, y, 50, 50);
        //TODO implement render map. Should be able to take any map. Then keep static content in one, and re-apply that after wipe.
        gameManager.render(gc, null);
        //gc.fillRect(x, y, 48, 48);
        //gc.fillRect(, , , , );
    }

    public StackPane sceneContent() {
        canvas = new Canvas(VisualInfo.WINDOW_WIDTH(), VisualInfo.WINDOW_HEIGHT());
        gc = canvas.getGraphicsContext2D();
        StackPane root = new StackPane(canvas);

        //buttonToMainMenuNavigation = new Button("Back to main menu");
        //StackPane layout = new StackPane(buttonToMainMenuNavigation);
        return root;
    }

    public void setNavigationButtons(Scene scene, StackPane mainManuPane) {
        //buttonToMainMenuNavigation.setOnAction(e -> scene.setRoot(mainManuPane));
    }

    public StackPane getContent() {
        return content;
    }
}
