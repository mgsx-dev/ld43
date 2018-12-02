package net.mgsx.ld43.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;

import net.mgsx.ld43.LD43;
import net.mgsx.ld43.assets.GameAssets;
import net.mgsx.ld43.utils.StageScreen;

public class PreLevelScreen extends StageScreen
{
	public PreLevelScreen() {
		super(new FitViewport(LD43.SCREEN_WIDTH, LD43.SCREEN_HEIGHT));
		Image back = new Image(GameAssets.i.prelevelTexture);
		back.setScaling(Scaling.fit);
		
		Table root = new Table();
		root.setFillParent(true);
		root.add(back).grow().center();
		
		stage.addActor(root);
		
		Table legend = new Table(GameAssets.i.skin);
		legend.setFillParent(true);
		stage.addActor(legend);

		legend.add("Pirate Overboard").expand().top();
		legend.row();
		legend.add("Level " + (LD43.i().metagame.level)).expand().bottom();
		
		stage.addAction(Actions.sequence(Actions.delay(2), Actions.run(new Runnable() {
			
			@Override
			public void run() {
				LD43.i().setScreen(new GameScreen());
			}
		})));
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		super.render(delta);
	}
}
