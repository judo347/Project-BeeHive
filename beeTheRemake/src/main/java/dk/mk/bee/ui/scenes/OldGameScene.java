package dk.mk.bee.ui.scenes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

//TODO may be deleted
@Deprecated
public class OldGameScene { //TODO rename to content or something?

    private StackPane content;
    private Button buttonToMainMenuNavigation;

    public OldGameScene() {
        this.content = sceneContent();
    }

    public StackPane sceneContent() {
        buttonToMainMenuNavigation = new Button("Back to main menu");
        StackPane layout = new StackPane(buttonToMainMenuNavigation);
        return layout;
    }

    public void setNavigationButtons(Scene scene, StackPane mainManuPane) {
        buttonToMainMenuNavigation.setOnAction(e -> scene.setRoot(mainManuPane));
    }

    public StackPane getContent() {
        return content;
    }
}
