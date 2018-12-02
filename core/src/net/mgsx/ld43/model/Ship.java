package net.mgsx.ld43.model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import net.mgsx.ld43.assets.GameAssets;
import net.mgsx.ld43.utils.FloattingAction;
import net.mgsx.ld43.utils.ThrowAction;

public class Ship {

	public Group shipGround;
	
	public Array<Canon> canons = new Array<Canon>();

	public Array<ShipPart> parts = new Array<ShipPart>();
	
	public ShipPart leftPart = null;

	public Rectangle r;
	
	private float hTime;

	private float baseX;

	private float baseY;
	
	public Ship() {
		Array<TiledMapTileMapObject> mos = new Array<TiledMapTileMapObject>();
		for(MapLayer layer : GameAssets.i.shipMap.getLayers()){
			if(layer.isVisible()){ // XXX
				mos.addAll(layer.getObjects().getByType(TiledMapTileMapObject.class));
			}
		}
		
		shipGround  = new Group();
		
		{
			
			Image img = new Image(new TextureRegionDrawable(GameAssets.i.regionBoatBase));
			img.setPosition(-50, 20); // XXX 
			img.setOrigin(Align.center);
			shipGround.addActor(img);
		}
		
		r = new Rectangle();
		
		for(TiledMapTileMapObject mo : mos){
			
			TextureRegion region = mo.getTextureRegion();
			Image img = new Image(new TextureRegionDrawable(region));
			img.setPosition(mo.getX(), mo.getY());
			img.setOrigin(Align.center);
			shipGround.addActor(img);
			
			ShipPart part = new ShipPart();
			part.baseX = mo.getX();
			part.baseY = mo.getY();
			
			part.img = img;
			img.setUserObject(part);
			
			parts.add(part);
			
			r.merge(img.getX(), img.getY());
			r.merge(img.getX(Align.right), img.getY(Align.bottom));
			
			if("canon".equals(mo.getName())){
				Canon canon = new Canon();
				canon.img = img;
				img.setUserObject(canon);
				
				canons.add(canon);
			}
		}
		
		shipGround.setOrigin(r.x + r.width/2, r.y+r.height);
		
		shipGround.addAction(new FloattingAction());
	}

	public Actor create() {

		return shipGround;
	}
	
	public void update(float delta){
		
		hTime += delta * .5f;
		
		float recule = MathUtils.lerp(-300, 300, MathUtils.sin(hTime) * .5f + .5f);
		
		shipGround.setPosition(baseX - recule, baseY);
		
		leftPart = null;
		for(ShipPart part : parts){
			if(part.disabled) continue;
			float px = part.img.getX();
			if(leftPart == null || px < leftPart.img.getX()){
				leftPart = part;
			}
		}
		
	}

	public void setBase(float x, float y) {
		baseX = x;
		baseY = y;
	}

	public Image ejectPart() {
		Vector2 p = leftPart.img.localToStageCoordinates(new Vector2());
		leftPart.img.setPosition(p.x, p.y);
		Image img = leftPart.img;
		
		leftPart.disabled = true;
		leftPart = null;
		
		
		img.addAction(Actions.sequence(Actions.parallel(new ThrowAction(900, 1600, 4500, 720)), Actions.removeActor()));
		
		return img;
	}
	
	
	
}
