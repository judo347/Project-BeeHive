package dk.mk.bee.scenes;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SceneLoader {

    private MainMenuScene mainMenuScene;
    private OptionsMenuScene optionsMenuScene;


    public void loadMainMenu() {
        mainMenuScene = new MainMenuScene();
    }

    public StackPane getMainMenu() {
        return mainMenuScene.getContent();
    }

    public void setNavigationButtons(Scene scene) {
        mainMenuScene.setNavigationButtons(scene);
    }
}
