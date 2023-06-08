package dk.mk.bee;

import dk.mk.bee.config.VisualInfo;
import dk.mk.bee.scenes.SceneLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

//How to run: use the maven plugin and run task: "javafx:run"
public class GameWindow extends Application {

    private SceneLoader sceneLoader;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(VisualInfo.TITLE);

        Scene scene = new Scene(new StackPane(), VisualInfo.WINDOW_WIDTH, VisualInfo.WINDOW_HEIGHT);
        primaryStage.setScene(scene);

        sceneLoader = new SceneLoader();
        sceneLoader.loadMainMenu();
        scene.setRoot(sceneLoader.getMainMenu());
        sceneLoader.setNavigationButtons(scene);
        primaryStage.setScene(scene);


        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
