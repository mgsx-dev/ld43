package net.mgsx.ld43.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import net.mgsx.ld43.LD43;
import net.mgsx.ld43.assets.GameAssets;
import net.mgsx.ld43.utils.StageScreen;

public class GameOverScreen extends StageScreen
{
	private Color bgColor = new Color(Color.DARK_GRAY);
	
	public GameOverScreen() {
		Table root = new Table(GameAssets.i.skin);
		root.setFillParent(true);
		stage.addActor(root);
		
		root.add("GAME OVER").expand().center();
		
		stage.addAction(Actions.sequence(
				Actions.delay(2f),
				Actions.run(new Runnable() {
					@Override
					public void run() {
						LD43.i().menu();
					}
				})
				));
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(bgColor.r, bgColor.g, bgColor.b, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		super.render(delta);
	}
}
