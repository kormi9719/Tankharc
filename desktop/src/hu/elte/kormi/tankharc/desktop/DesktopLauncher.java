package hu.elte.kormi.tankharc.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import hu.elte.kormi.helpers.GameInfo;
import hu.elte.kormi.tankharc.GameMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = GameInfo.WIDTH;
		config.height = GameInfo.HEIGHT;
		config.resizable = false;

		new LwjglApplication(new GameMain(), config);
	}
}
