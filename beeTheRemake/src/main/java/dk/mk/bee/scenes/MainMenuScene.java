package dk.mk.bee.scenes;

import dk.mk.bee.config.GameInfo;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenuScene { //TODO rename to content or something?
    private OptionsMenuScene optionsMenuScene;
    private GameScene gameScene;
    private Button buttonOptionsManuNavigation;
    private Button buttonStartGameNavigation;
    private Button buttonExitNavigation;

    private StackPane content;

    public MainMenuScene() {
        this.content = sceneContent();
        this.optionsMenuScene = new OptionsMenuScene();
        this.gameScene = new GameScene();
    }

    private StackPane sceneContent() {
        buttonOptionsManuNavigation = new Button("Options");
        buttonStartGameNavigation = new Button("Start");
        buttonExitNavigation = new Button("Exit");
        VBox hBox = new VBox(buttonStartGameNavigation, buttonOptionsManuNavigation, buttonExitNavigation);
        StackPane layout = new StackPane(hBox);
        return layout;
    }

    public void setNavigationButtons(Scene scene) {
        buttonOptionsManuNavigation.setOnAction(e -> scene.setRoot(optionsMenuScene.getContent()));
        buttonExitNavigation.setOnAction(e -> ((Stage)scene.getWindow()).close());
        buttonStartGameNavigation.setOnAction(e -> scene.setRoot(gameScene.getContent()));
        optionsMenuScene.setNavigationButtons(scene, content);
        gameScene.setNavigationButtons(scene, getContent());
    }

    public StackPane getContent() {
        return content;
    }
}
