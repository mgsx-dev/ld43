package net.mgsx.ld43;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import net.mgsx.ld43.assets.GameAssets;
import net.mgsx.ld43.model.MetaGame;
import net.mgsx.ld43.screens.GameScreen;
import net.mgsx.ld43.screens.PreLevelScreen;

public class LD43 extends Game{

	public static final int SCREEN_WIDTH = 1024;
	public static final int SCREEN_HEIGHT = 512;

	public MetaGame metagame = new MetaGame();
	
	public static LD43 i(){
		return (LD43) Gdx.app.getApplicationListener();
	}
	
	@Override
	public void create() {
		GameAssets.i = new GameAssets();
		
		// setScreen(new PreLevelScreen());
		
		// XXX 
		setScreen(new GameScreen());
	}

	public void menu() {
		metagame = new MetaGame();
		
		// XXX 
		setScreen(new PreLevelScreen());
	}

}
