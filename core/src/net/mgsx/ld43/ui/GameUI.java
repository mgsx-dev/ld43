package net.mgsx.ld43.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;

import net.mgsx.ld43.LD43;
import net.mgsx.ld43.assets.GameAssets;
import net.mgsx.ld43.model.Ship;
import net.mgsx.ld43.model.ShipPart;
import net.mgsx.ld43.screens.GameScreen;
import net.mgsx.ld43.screens.PreLevelScreen;

public class GameUI extends Table
{
	private Image imgLife;
	private Image backLife;
	
	private float lifeWidthMax = 500;
	private GameScreen screen;
	
	private float lifeBlink;
	
	private boolean modePickup = false;

	public GameUI(GameScreen screen) {
		super(GameAssets.i.skin);
		this.screen = screen;
		setFillParent(true);
		
		backLife = new Image(getSkin(), "white"); 
		backLife.setColor(.3f, .4f, .5f, 1);
		backLife.setSize(lifeWidthMax + 4, 50);
		
		imgLife = new Image(getSkin(), "white");
		imgLife.setPosition(2, 2);
		imgLife.setColor(.6f,.8f,1, 1f);
		imgLife.setSize(0, backLife.getHeight() - 4);
		
		Group groupLife = new Group();
		groupLife.addActor(backLife);
		groupLife.addActor(imgLife);
		
		groupLife.setPosition(50, 50);
		
		addActor(groupLife);
		
		add("Level " + LD43.i().metagame.level).expand().bottom().right().pad(10).getActor().setFontScale(2);
	}
	
	@Override
	public void act(float delta) {
		
		
		float lifeRateSmooth = screen.shark.sharkLifeSmooth / screen.shark.sharkLifeMax;
		float lifeRate = screen.shark.sharkLife / screen.shark.sharkLifeMax;
		float deltaRate = Math.abs(lifeRateSmooth - lifeRate);
		
		lifeBlink += delta * 60;
		float blink = MathUtils.sin(lifeBlink) * 1.5f + .5f;
		
		if(lifeRateSmooth <lifeRate){
			blink = 0;
			deltaRate = 0;
		}
		
		if(screen.shark.stunt){
			imgLife.getColor().set(Color.GREEN);
		}else{
			imgLife.getColor().set(.6f,.8f,1, 1);
		}
		
		imgLife.getColor().a = MathUtils.lerp(1, blink, MathUtils.clamp(deltaRate * 4, 0, 1));
		
		imgLife.setWidth(lifeWidthMax * MathUtils.clamp(lifeRateSmooth, 0, 1));
		super.act(delta);
	}

	public void launchPickup() {
		if(modePickup) return;
		modePickup = true;
		
		LD43.i().metagame.credits += LD43.i().metagame.level;
		
		Table menu = new Table(getSkin());
		menu.setBackground(getSkin().newDrawable("white", Color.DARK_GRAY));
		menu.add("Level " + LD43.i().metagame.level + " Rewards").getActor().setFontScale(2);
		menu.row();
		
		final Label labelChooseUp = new Label("", getSkin());
		menu.add(labelChooseUp);
		updateChooseUpLabel(labelChooseUp);
		
		menu.setSize(getStage().getWidth() * .7f, getStage().getHeight() * .7f);
		menu.setTransform(true);
		menu.setScale(0);
		menu.setOrigin(Align.right);
		menu.setPosition(getStage().getWidth()/2, getStage().getHeight()/2, Align.center);
		
		menu.addAction(Actions.sequence(
				Actions.delay(5),
				Actions.scaleTo(1, 1, 1f, Interpolation.swingOut),
				Actions.touchable(Touchable.enabled)));
		
		
		
		Table itemList = new Table(getSkin());
		menu.row();
		menu.add(itemList).row();
		
		Label btOK = new Label("Let's GO !", getSkin());
		btOK.setTouchable(Touchable.enabled);
		menu.add(btOK).expandX().center();
		
		btOK.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				end();
			}
		});
		
		getStage().addActor(menu);
		
		final Ship ship = LD43.i().metagame.ship;
		int count = 0;
		for(final ShipPart part : ship.parts){
			if(part.disabled){
				
				final Image imgPart = new Image(part.img.getDrawable());
				imgPart.setScaling(Scaling.fit);
				itemList.add(imgPart).size(100);
				
				if(count % 8 == 7) itemList.row();
				count++;
				
				imgPart.addListener(new ClickListener(){
					@Override
					public void clicked(InputEvent event, float x, float y) {
						ship.restorePart(part);
						LD43.i().metagame.credits--;
						updateChooseUpLabel(labelChooseUp);
						if(LD43.i().metagame.credits <= 0){
							setTouchable(Touchable.disabled);
							addAction(Actions.sequence(Actions.delay(1f), Actions.run(new Runnable() {
								@Override
								public void run() {
									end();
								}
							})));
						}
						imgPart.setTouchable(Touchable.disabled);
						imgPart.addAction(Actions.alpha(0, .3f));
					}
				});
			}
			// XXX
			if(count > 32) break;
		}
		
		
		
	}
	
	private void updateChooseUpLabel(Label label){
		label.setText("Choose up to " + LD43.i().metagame.credits + " stuff to repair");
	}
	
	private void end(){
		LD43.i().metagame.level++;
		LD43.i().setScreen(new PreLevelScreen());
	}
}
