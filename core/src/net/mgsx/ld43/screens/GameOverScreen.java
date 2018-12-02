package net.mgsx.ld43.screens;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import net.mgsx.ld43.LD43;
import net.mgsx.ld43.assets.GameAssets;
import net.mgsx.ld43.utils.StageScreen;

public class GameOverScreen extends StageScreen
{
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
}
