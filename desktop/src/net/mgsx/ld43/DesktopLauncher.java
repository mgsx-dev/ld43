package net.mgsx.ld43;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = LD43.SCREEN_WIDTH;
		config.height = LD43.SCREEN_HEIGHT;
		
		new LwjglApplication(new LD43(), config);
	}
}
