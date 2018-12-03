package net.mgsx.ld43.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;

import net.mgsx.ld43.LD43;
import net.mgsx.ld43.assets.AudioEngine;
import net.mgsx.ld43.assets.GameAssets;
import net.mgsx.ld43.utils.StageScreen;

public class TitleScreen extends StageScreen
{
	public TitleScreen() {
		super(new FitViewport(LD43.SCREEN_WIDTH, LD43.SCREEN_HEIGHT));
		Image back = new Image(GameAssets.i.titleTexture);
		back.setScaling(Scaling.fit);
		
		Table root = new Table();
		root.setFillParent(true);
		root.add(back).grow().center();
		
		stage.addActor(root);
		
		Table legend = new Table(GameAssets.i.skin);
		legend.setFillParent(true);
		stage.addActor(legend);

//		legend.add("Pirate Overboard").expand().top();
//		legend.row();
//		legend.add("Level " + (LD43.i().metagame.level)).expand().bottom();
		
		Label bt = new Label("Touch to Start", GameAssets.i.skin);
		bt.setFontScale(1);
		
		bt.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				LD43.i().setScreen(new PreLevelScreen());
			}
		});
		
		Table t = new Table();
		t.setTransform(true);
		t.add(bt).expand().center();
		t.validate();
		t.setOrigin(Align.center);
		
		float s = 1.3f;
		float d = .3f;
		
		t.addAction(Actions.forever(
				Actions.sequence(Actions.scaleTo(s, s, d), 
				Actions.sequence(Actions.scaleTo(1, 1, d)))));
		
		stage.addActor(t);
		t.setPosition(stage.getWidth()/2, stage.getHeight()/2, Align.center);
		
		AudioEngine.i.playMusic(1);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		super.render(delta);
	}
}
