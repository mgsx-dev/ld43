package net.mgsx.ld43;

import com.badlogic.gdx.Game;

import net.mgsx.ld43.screens.GameScreen;

public class LD43 extends Game{

	public static final int SCREEN_WIDTH = 1024;
	public static final int SCREEN_HEIGHT = 512;

	@Override
	public void create() {
		
		setScreen(new GameScreen());
		
	}

}
