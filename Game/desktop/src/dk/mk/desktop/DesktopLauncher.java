package dk.mk.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import dk.mk.GameInfo;
import dk.mk.GdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = GameInfo.WINDOW_WIDTH;
		config.height = GameInfo.WINDOW_HEIGHT;
		config.foregroundFPS = GameInfo.FOREGROUND_FPS;
		config.title = GameInfo.TITLE;


		new LwjglApplication(new GdxGame(), config);
	}
}
