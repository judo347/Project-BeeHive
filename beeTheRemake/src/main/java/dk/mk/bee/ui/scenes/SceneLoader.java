package dk.mk.bee.ui.scenes;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

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
