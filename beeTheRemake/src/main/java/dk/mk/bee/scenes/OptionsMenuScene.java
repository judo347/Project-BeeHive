package dk.mk.bee.scenes;

import dk.mk.bee.config.GameInfo;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class OptionsMenuScene { //TODO rename to content or something?

    private StackPane content;
    private Button buttonBackNavigation;

    public OptionsMenuScene() {
        this.content = sceneContent();
    }

    public StackPane sceneContent() {
        buttonBackNavigation = new Button("Back to Main Menu");
        StackPane layout = new StackPane(buttonBackNavigation);
        return layout;
    }

    public void setNavigationButtons(Scene scene, StackPane mainManuPane) {
        buttonBackNavigation.setOnAction(e -> scene.setRoot(mainManuPane));
    }

    public StackPane getContent() {
        return content;
    }
}
