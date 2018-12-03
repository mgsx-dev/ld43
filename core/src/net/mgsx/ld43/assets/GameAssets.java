package net.mgsx.ld43.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
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
	
	public Skin skin;


	private Texture bgTexture2;

	public TextureRegion regionIslandEnd;


	public Texture prelevelTexture;

	public Texture textsTexture;

	
	public Texture titleTexture;


	public Array<TextureRegion> textsRegions;


	public Animation<TextureRegion> sharkAnimationEat;
	
	public GameAssets() 
	{
		AudioEngine.i = new AudioEngine();
		
		skin = new Skin(Gdx.files.internal("skins/game-skin.json"));
		
		shipMap = new TmxMapLoader().load("ship.tmx");

		Texture tilesetTexture = shipMap.getTileSets().getTileSet(0).iterator().next().getTextureRegion().getTexture();
		
		Texture sharkTexture = new Texture(Gdx.files.local("shark.png"));
		Texture sharkTexture2 = new Texture(Gdx.files.local("shark2.png"));

		sharkFrames = new Array<TextureRegion>();
		sharkFrames.add(new TextureRegion(sharkTexture, 0, 1024 - 128 * 3, 1024, 128 * 3));
		sharkFrames.add(new TextureRegion(sharkTexture, 0, 1024 - 128 * 6, 1024, 128 * 3));
		sharkFrames.add(new TextureRegion(sharkTexture, 0, 1024 - 128 * 8, 1024, 128 * 2));
		
		sharkFrames.add(new TextureRegion(sharkTexture2, 0, 1024 - 128 * 3, 1024, 128 * 3));
		sharkFrames.add(new TextureRegion(sharkTexture2, 0, 1024 - 128 * 6, 1024, 128 * 3));
		sharkFrames.add(new TextureRegion(sharkTexture2, 0, 1024 - 128 * 8, 1024, 128 * 2)); // 5 => frappé

		Array<TextureRegion> someFrames = new Array<TextureRegion>();
		someFrames.add(sharkFrames.get(3));
		someFrames.add(sharkFrames.get(4));
		
		sharkAnimationEat = new Animation<TextureRegion>(1, someFrames);
		
		someFrames = new Array<TextureRegion>();
		someFrames.add(sharkFrames.get(0));
		someFrames.add(sharkFrames.get(1));
		sharkAnimation = new Animation<TextureRegion>(1, someFrames);
		
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

		bgTexture = new Texture(Gdx.files.local("background.png"));


		
		regionWater = new TextureRegion(bgTexture, 0, 1024 - 128 * 5, 1024, 128 * 2);
		regionIsland = new TextureRegion(bgTexture, 0, 1024 - 128 * 3, 1024, 128 * 3);
		regionSky = new TextureRegion(bgTexture, 0, 1024 - 128 * 8, 1024, 128 * 3);
		
		
		bgTexture2 = new Texture(Gdx.files.local("background2.png"));

		regionIslandEnd = new TextureRegion(bgTexture2, 0, 1024 - 128 * 6, 1024, 128 * 6);
		
		prelevelTexture = new Texture(Gdx.files.local("prelevel.png"));
		titleTexture = new Texture(Gdx.files.local("titleScreen.png"));
		
		textsTexture = new Texture(Gdx.files.local("texts.png"));
		
		textsRegions = new Array<TextureRegion>();
		for(int y=0 ; y<4 ; y++){
			for(int x=0 ; x<4 ; x++){
				textsRegions.add(new TextureRegion(textsTexture, x * 256, y * 256, 256, 256));;
			}
		}

	}
}
