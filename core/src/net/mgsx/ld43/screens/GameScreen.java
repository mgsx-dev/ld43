package net.mgsx.ld43.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

import net.mgsx.ld43.LD43;
import net.mgsx.ld43.utils.FloattingAction;
import net.mgsx.ld43.utils.StageScreen;
import net.mgsx.ld43.utils.ThrowAction;
import net.mgsx.ld43.utils.WaterAction;

public class GameScreen extends StageScreen
{

	private Vector2 targetFrom = new Vector2();
	private Vector2 targetTo = new Vector2();
	
	
	private Actor targetActor;
	
	private ShapeRenderer renderer;
	
	private Group shipGround = new Group();
	
	public GameScreen() {
		
		super(new FitViewport(LD43.SCREEN_WIDTH * 2f, LD43.SCREEN_HEIGHT * 2f));
		
		renderer = new ShapeRenderer();
		
		// convert ship to actors
		TiledMap shipMap = new TmxMapLoader().load("../../assets/src/ship.tmx");
		
		Array<TiledMapTileMapObject> mos = new Array<TiledMapTileMapObject>();
		for(MapLayer layer : shipMap.getLayers()){
			mos.addAll(layer.getObjects().getByType(TiledMapTileMapObject.class));
		}
	
		
		
		shipGround.addAction(new FloattingAction());
		
		Rectangle r = new Rectangle();
		
		Texture bgTexture = new Texture(Gdx.files.local("../../assets/src/background.png"));
		Texture sharkTexture = new Texture(Gdx.files.local("../../assets/src/shark.png"));

		

		
		TextureRegion regionWater = new TextureRegion(bgTexture, 0, 1024 - 128 * 5, 1024, 128 * 2);
		TextureRegion regionIsland = new TextureRegion(bgTexture, 0, 1024 - 128 * 3, 1024, 128 * 3);
		TextureRegion regionSky = new TextureRegion(bgTexture, 0, 1024 - 128 * 8, 1024, 128 * 3);

		for(int i=0 ; i<3 ; i++){
			Image imgWater = new Image(new TextureRegionDrawable(regionSky));
			imgWater.setTouchable(Touchable.disabled);
			stage.addActor(imgWater);
			imgWater.setX(i * imgWater.getWidth());
			imgWater.setY(600);
			imgWater.addAction(new WaterAction(.1f)); 
		}
		
		for(int i=0 ; i<3 ; i++){
			Image imgWater = new Image(new TextureRegionDrawable(regionIsland));
			imgWater.setTouchable(Touchable.disabled);
			stage.addActor(imgWater);
			imgWater.setX(i * imgWater.getWidth());
			imgWater.setY(100);
			imgWater.addAction(new WaterAction(.3f)); 
		}
		
		stage.addActor(shipGround);
		
		for(int i=0 ; i<3 ; i++){
			Image imgWater = new Image(new TextureRegionDrawable(regionWater));
			imgWater.setTouchable(Touchable.disabled);
			stage.addActor(imgWater);
			imgWater.setColor(Color.GRAY);
			imgWater.setX(i * imgWater.getWidth());
			imgWater.setY(-40);
			imgWater.addAction(new WaterAction(2f)); 
		}
		
		TextureRegion regionShark = new TextureRegion(sharkTexture, 0, 1024 - 128 * 3, 1024, 128 * 3);
		
		Image imgShark = new Image(new TextureRegionDrawable(regionShark));
		imgShark.setTouchable(Touchable.disabled);
		stage.addActor(imgShark);
		imgShark.setOrigin(Align.center);
		imgShark.addAction(new FloattingAction());
		
		for(int j=0 ; j< 1 ; j++){
			for(int i=0 ; i<3 ; i++){
				Image imgWater = new Image(new TextureRegionDrawable(regionWater));
				imgWater.setTouchable(Touchable.disabled);
				stage.addActor(imgWater);
				imgWater.getColor().a = 0.7f;
				imgWater.setX(i * imgWater.getWidth());
				imgWater.addAction(new WaterAction(3f)); 
			}
		}
		
		
		TextureRegion regionBoatBase = new TextureRegion(mos.get(0).getTextureRegion().getTexture(), 0, 1024 - 128 * 4, 128 * 5, 128 * 4);
		{
			
			Image img = new Image(new TextureRegionDrawable(regionBoatBase));
			img.setPosition(-50, 20); // XXX 
			img.setOrigin(Align.center);
			shipGround.addActor(img);
		}
		
		for(TiledMapTileMapObject mo : mos){
			TextureRegion region = mo.getTextureRegion();
			Image img = new Image(new TextureRegionDrawable(region));
			img.setPosition(mo.getX(), mo.getY());
			img.setOrigin(Align.center);
			shipGround.addActor(img);
			
			r.merge(img.getX(), img.getY());
			r.merge(img.getX(Align.right), img.getY(Align.bottom));
			
			img.addListener(new DragListener(){
				@Override
				public void dragStart(InputEvent event, float x, float y, int pointer) {
					Actor actor = event.getListenerActor();
					targetFrom.set(actor.getX(Align.center), actor.getY(Align.center));
					targetActor = actor;
				}
				@Override
				public void drag(InputEvent event, float x, float y, int pointer) {
					Actor actor = event.getListenerActor();
					
					actor.localToStageCoordinates(targetTo.set(x, y));
				}
				@Override
				public void dragStop(InputEvent event, float x, float y, int pointer) {
					Actor actor = event.getListenerActor();
					
					float dx = targetTo.x - targetFrom.x;
					float dy = targetTo.y - targetFrom.y;
					float s = 10;
					dx *= s;
					dy *= s;
					
					targetActor.localToStageCoordinates(targetFrom.setZero());
					targetActor.setPosition(targetFrom.x, targetFrom.y);
					stage.addActor(targetActor);
					
					targetActor = null;
					
					actor.addAction(Actions.sequence(
							new ThrowAction(dx, dy, 5000f, 720)
							));
				}
			});
		}
		
		shipGround.setPosition(stage.getViewport().getWorldWidth() - r.width * 1.3f, 140);

		shipGround.setOrigin(r.x + r.width/2, r.y+r.height);
		
		// stage.setDebugAll(true);
	}
	
	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(.7f, .9f, .95f, 0);
		// Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		super.render(delta);
		
		renderer.setProjectionMatrix(stage.getCamera().combined);
		
		renderer.begin(ShapeType.Line);
		if(targetActor != null){
			
			targetFrom.set(targetActor.getWidth()/2, targetActor.getHeight()/2);
			targetActor.localToStageCoordinates(targetFrom);

			
			renderer.line(targetFrom, targetTo);
		}
		renderer.end();
	}
}
