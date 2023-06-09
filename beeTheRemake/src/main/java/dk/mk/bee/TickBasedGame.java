package dk.mk.bee;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

//TODO may be deleted
public class TickBasedGame extends Application {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int TICK_INTERVAL = 16; // Milliseconds (approximately 60 ticks per second)

    private Canvas canvas;
    private GraphicsContext gc;
    private long lastTickTime;

    // Game state variables
    private int x;
    private int y;

    @Override
    public void start(Stage primaryStage) {
        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();

        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        primaryStage.setTitle("Tick-Based Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Initialize game state
        x = WIDTH / 2;
        y = HEIGHT / 2;

        // Start the game loop
        lastTickTime = System.currentTimeMillis();
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long currentTickTime = System.currentTimeMillis();
                if (currentTickTime - lastTickTime >= TICK_INTERVAL) {
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
        gc.clearRect(0, 0, WIDTH, HEIGHT);

        // Render the game objects
        gc.fillOval(x, y, 50, 50);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
