package net.mgsx.ld43.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Scroller extends Group
{
	public float speedTransition = 1;
	
	public float speedBase;
	public float speedFactor;
	private float width, loopWidth;
	
	public Scroller(TextureRegion region) {
		this(region, Color.WHITE);
	}
	
	public Scroller(TextureRegion region, Color color) {
		for(int i=0 ; i<3 ; i++){
			Image imgWater = new Image(new TextureRegionDrawable(region));
			imgWater.setTouchable(Touchable.disabled);
			imgWater.setX(i * imgWater.getWidth());
			imgWater.setColor(color);
			addActor(imgWater);
			
			width += imgWater.getWidth();
		}
		loopWidth = width/3;
	}

	public void update(float delta) 
	{
		float speed = MathUtils.lerp(speedBase, speedFactor, speedTransition);
		
		setX(getX() - delta * speed * 500);
		if(getX() < - loopWidth){
			setX(getX() + loopWidth);
		}
		
	}

}
