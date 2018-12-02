package net.mgsx.ld43.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;

public class GameAssets {
	public static GameAssets i;
	
	
	public Array<TextureRegion> sharkFrames;
	public Animation<TextureRegion> sharkAnimation;
	
	public Array<TextureRegion> canonFrames;
	public Animation<TextureRegion> canonAnimation;
	
	
	public TiledMap shipMap;
	public TextureRegion regionBullet;
	public TextureRegion regionBoatBase;

	public Texture bgTexture;
	public TextureRegion regionWater;
	public TextureRegion regionIsland;
	public TextureRegion regionSky; 
	
	public GameAssets() 
	{
		shipMap = new TmxMapLoader().load("../../assets/src/ship.tmx");

		Texture tilesetTexture = shipMap.getTileSets().getTileSet(0).iterator().next().getTextureRegion().getTexture();
		
		Texture sharkTexture = new Texture(Gdx.files.local("../../assets/src/shark.png"));

		sharkFrames = new Array<TextureRegion>();
		sharkFrames.add(new TextureRegion(sharkTexture, 0, 1024 - 128 * 3, 1024, 128 * 3));
		sharkFrames.add(new TextureRegion(sharkTexture, 0, 1024 - 128 * 6, 1024, 128 * 3));
		sharkFrames.add(new TextureRegion(sharkTexture, 0, 1024 - 128 * 8, 1024, 128 * 2));
		
		sharkAnimation = new Animation<TextureRegion>(1, sharkFrames);
		
		// TextureRegion regionShark = new TextureRegion(sharkTexture, 0, 1024 - 128 * 3, 1024, 128 * 3);
		
		{
			canonFrames = new Array<TextureRegion>();
			for(int i=0 ; i<4 ; i++){
				final TextureRegion reg = new TextureRegion(tilesetTexture,  128 * i, 1024 - 128 * 5, 128, 128);
				canonFrames.add(reg);
			}
			
			
			canonAnimation = new Animation<TextureRegion>(1, canonFrames);
		}
		
		regionBullet = new TextureRegion(tilesetTexture,  128 * 4, 1024 - 128 * 5, 128, 128);
		
		regionBoatBase = new TextureRegion(tilesetTexture, 0, 1024 - 128 * 4, 128 * 5, 128 * 4);

		bgTexture = new Texture(Gdx.files.local("../../assets/src/background.png"));

		
		regionWater = new TextureRegion(bgTexture, 0, 1024 - 128 * 5, 1024, 128 * 2);
		regionIsland = new TextureRegion(bgTexture, 0, 1024 - 128 * 3, 1024, 128 * 3);
		regionSky = new TextureRegion(bgTexture, 0, 1024 - 128 * 8, 1024, 128 * 3);

	}
}
