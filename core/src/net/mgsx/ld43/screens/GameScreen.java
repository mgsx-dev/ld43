package net.mgsx.ld43.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

import net.mgsx.ld43.LD43;
import net.mgsx.ld43.assets.AudioEngine;
import net.mgsx.ld43.assets.GameAssets;
import net.mgsx.ld43.model.Canon;
import net.mgsx.ld43.model.Shark;
import net.mgsx.ld43.model.Ship;
import net.mgsx.ld43.model.ShipPart;
import net.mgsx.ld43.ui.GameUI;
import net.mgsx.ld43.utils.BlinkAction;
import net.mgsx.ld43.utils.StageScreen;
import net.mgsx.ld43.utils.ThrowAction;

public class GameScreen extends StageScreen
{
	private static final boolean drawDebug = false;

	private Vector2 targetFrom = new Vector2();
	private Vector2 targetTo = new Vector2();
	
	
	private Actor targetActor, dropActor;
	
	private ShapeRenderer renderer;
	
	private Actor shootingCanon;
	
	public Shark shark;
	
	private Circle dropCircle = new Circle();

	private Ship ship;
	
	private Scroller waterBgScroller, waterFgScroller, earthScroller, skyScroller;
	
	private Array<Scroller> scrollers = new Array<Scroller>();

	private GameUI gameUI;
	
	private Vector2 shootVector = new Vector2();

	private Image imgIslandEnd;
	
	public GameScreen() {
		
		super(new FitViewport(LD43.SCREEN_WIDTH * 2f, LD43.SCREEN_HEIGHT * 2f));
		
		AudioEngine.i.playMusic(3);
		
		renderer = new ShapeRenderer();
		
		if(LD43.i().metagame.ship == null){
			ship = LD43.i().metagame.ship = new Ship();
		}else{
			ship = LD43.i().metagame.ship;
			ship.enable();
		}
		
		Actor shipGround = ship.create();
		ship.setBase(stage.getViewport().getWorldWidth() - ship.r.width * 1.3f, 140); 
		
		
		stage.addActor(skyScroller = new Scroller(GameAssets.i.regionSky));
		skyScroller.setY(600);
		skyScroller.speedFactor = .1f;
		skyScroller.speedBase = .03f;
		
		stage.addActor(earthScroller = new Scroller(GameAssets.i.regionIsland));
		earthScroller.setY(100);
		earthScroller.speedFactor = .3f;
		earthScroller.speedBase = 0f;
		
		
		imgIslandEnd = new Image(GameAssets.i.regionIslandEnd);
		stage.addActor(imgIslandEnd);
		
		
		stage.addActor(waterBgScroller = new Scroller(GameAssets.i.regionWater, Color.GRAY));
		waterBgScroller.setY(-40);
		waterBgScroller.speedFactor = 2f;
		waterBgScroller.speedBase = .1f;
		
		stage.addActor(shipGround);
		
		shark = new Shark(LD43.i().metagame.level);
		stage.addActor(shark.create());
		
		stage.addActor(waterFgScroller = new Scroller(GameAssets.i.regionWater, new Color(1,1,1,.7f)));
		waterFgScroller.setY(0);
		waterFgScroller.speedFactor = 3f;
		waterFgScroller.speedBase = .2f;
		
		for(ShipPart part : ship.parts){
			
			part.img.addListener(new DragListener(){
				
				@Override
				public void dragStart(InputEvent event, float x, float y, int pointer) {
					Actor actor = event.getListenerActor();
					targetFrom.set(actor.getX(Align.center), actor.getY(Align.center));
					
					targetActor = actor;
					
					if(actor.getUserObject() instanceof Canon){
						Canon canon = (Canon)actor.getUserObject();
						if(canon.charged()){
							shootingCanon = actor;
							canon.targetting = true;
						}
					}
					if(shootingCanon == null){
						targetActor.addAction(new BlinkAction(8f));// XXX
					}
					
					AudioEngine.i.playSFXRandom(7, 8, 9);
					
				}
				@Override
				public void drag(InputEvent event, float x, float y, int pointer) {
					Actor actor = event.getListenerActor();
					
					actor.localToStageCoordinates(targetTo.set(x, y));
					if(shootingCanon != null){
						float angle = targetTo.cpy().sub(targetFrom).angle() + 180 - shootingCanon.getParent().getRotation(); // XXX optim cpy
						shootingCanon.setRotation(angle * 1f);
					}
				}
				@Override
				public void dragStop(InputEvent event, float x, float y, int pointer) {
					// Actor actor = event.getListenerActor();
					
					// max force at 1
					float force = 3000;
					
					ShipPart dragPart = ((ShipPart)targetActor.getUserObject());
					
					if(shootingCanon != null){
						
						AudioEngine.i.playSFXRandom(5, 6);
						
						((Canon)shootingCanon.getUserObject()).shoot();
						
						Image bullet = new Image(new TextureRegionDrawable(GameAssets.i.regionBullet));
						bullet.setPosition(targetFrom.x, targetFrom.y);
						bullet.setOrigin(Align.center);
						
						ShipPart part = ShipPart.bullet();
						bullet.setUserObject(part);
						
						shootingCanon = null;
						targetActor = bullet;
					}
					else{
						if("pirate".equals(dragPart.name)){
							AudioEngine.i.playSFXRandom(10, 11);
						}else{
							// TODO global throw
						}
						targetActor.clearActions();
						targetActor.setVisible(false);
						targetActor.setTouchable(Touchable.disabled);
						Drawable drawable = ((Image)targetActor).getDrawable();
						float tx = targetActor.getX();
						float ty = targetActor.getY();
						
						targetActor = new Image(drawable);
						targetActor.setPosition(tx, ty);
						targetActor.setUserObject(dragPart);
						targetActor.setOrigin(Align.center);
						ship.shipGround.addActor(targetActor);
					}
					
					dragPart.disabled = true;

					targetActor.clearActions();
					targetActor.setColor(Color.WHITE);
					targetActor.setScale(1.5f); // XXX
					
					targetActor.localToStageCoordinates(targetFrom.setZero());
					targetActor.setPosition(targetFrom.x, targetFrom.y);
					stage.addActor(targetActor);
					
					dropActor = targetActor;
					targetActor = null;
					
					dropActor.clearListeners();
					
					dropActor.addAction(Actions.sequence(
							new ThrowAction(shootVector.x * force, shootVector.y * force, 5000f, 720)
							));
					
					// XXX delete copy after 5s !
					dropActor.addAction(Actions.sequence(Actions.delay(5), Actions.removeActor()));
				}
			});
		}
		
		// stage.setDebugAll(true);
		
		scrollers.addAll(waterBgScroller, waterFgScroller, earthScroller, skyScroller);
		
		stage.addActor(gameUI = new GameUI(this));
		
	}
	
	private float worldSpeedFactor = 1;
	
	private boolean endTrigger;
	
	@Override
	public void render(float delta) {
		
		for(Scroller scroller : scrollers){
			
			scroller.speedTransition = worldSpeedFactor; // TODO set depends on arriving island ....
			
			scroller.update(delta);
		}
		
		for(Canon model : ship.canons){
			model.update(delta);
		}
		
		shark.update(delta);
		ship.update(delta);
		
		if(shark.sharkLife <= 0){
			
			if(!endTrigger){
				endTrigger = true;
				AudioEngine.i.playMusic(0);
			}
			
			gameUI.launchPickup();
			
			worldSpeedFactor -= delta / 5f; // 5 seconds to stop
			worldSpeedFactor = Math.max(0, worldSpeedFactor);
			
			ship.endFactor = Interpolation.pow2Out.apply(1 - worldSpeedFactor);
			
			ship.disable();
		}
		else if(ship.isDead){
			
			imgIslandEnd.remove();
			
			worldSpeedFactor -= delta / 5f; // 5 seconds to stop
			if(worldSpeedFactor < 0){
				worldSpeedFactor = 0;
				
				LD43.i().setScreen(new GameOverScreen());
			}
			
			ship.endFactor = Interpolation.pow2Out.apply(1 - worldSpeedFactor);
			
			ship.disable();
			
		}
		
		
		imgIslandEnd.setX(MathUtils.lerp(2100 + 300, 1050, ship.endFactor));
		
		if(dropActor != null){
			dropCircle.set(dropActor.getX() + dropActor.getWidth()/2,  dropActor.getY() + dropActor.getHeight()/2, dropActor.getWidth() * .8f);
			if(shark.circleShaper.overlaps(dropCircle)){
				// TODO hurt
				dropActor.clearActions();
				dropActor.addAction(new ThrowAction(-800, 300, -100, 730));
				dropActor.addAction(Actions.sequence(Actions.parallel(Actions.alpha(0, .5f, Interpolation.pow3In), Actions.scaleTo(3, 3, .5f)), Actions.removeActor()));
			
				ShipPart part = (ShipPart) dropActor.getUserObject();
				shark.hurted(part);
				part.playImpact();
				
				dropActor = null;
				
				
				
			}
		}
		
		
		// proximity whith ship ...
		if(ship.leftPart != null && !shark.attacking && !shark.stunt){
			if(shark.circleShaper.x + shark.circleShaper.radius > ship.leftPart.img.getX() + ship.shipGround.getX() + 200){
				
				shark.attack();
				
				stage.addActor(ship.ejectPart());
			}
		}
		
		// TODO check ship dead or shark dead ...
		
		
		Gdx.gl.glClearColor(.7f, .9f, .95f, 0);
		// Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		super.render(delta);
		
		if(targetActor != null){
			
			targetFrom.set(targetActor.getWidth()/2, targetActor.getHeight()/2);
			targetActor.localToStageCoordinates(targetFrom);
			
			// TODO 600 is user distancemax for shots
			shootVector.set(targetTo).sub(targetFrom).scl(1f / 600f);
			
			// limit
			float len = shootVector.len();
			
			if(len > 1){
				len = 1;
			}
			shootVector.nor().scl(len);
		}
		
		if(drawDebug){
			
			renderer.setProjectionMatrix(stage.getCamera().combined);
			
			renderer.begin(ShapeType.Line);
			if(targetActor != null){
				
				renderer.line(targetFrom, targetTo);
				
				renderer.setColor(Color.RED);
				renderer.line(targetFrom, shootVector.cpy().scl(600).add(targetFrom)); // XXX cpy
				renderer.setColor(Color.WHITE);
			}
			
			if(dropActor != null){
				renderer.circle(dropCircle.x, dropCircle.y, dropCircle.radius, 16);
				
			}
			
			renderer.circle(shark.circleShaper.x, shark.circleShaper.y, shark.circleShaper.radius, 16);
			
			renderer.end();
		}
		
	}
}
