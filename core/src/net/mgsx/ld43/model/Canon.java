package net.mgsx.ld43.model;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import net.mgsx.ld43.assets.GameAssets;

public class Canon {
	public float chargeTime;
	public float frameTime;
	
	public Image img;
	
	public boolean shooting;
	
	private float shootTime;
	
	public boolean targetting;
	
	public void update(float delta){
		
		frameTime += delta;
		
		
		if(!shooting){
			
			chargeTime += delta * .3f;
			chargeTime = Math.min(1, chargeTime);
			
			if(charged()){
				img.setScale(MathUtils.lerp(1f, 1.5f, MathUtils.sin(frameTime * 10) * .5f + .5f));
			}else{
				img.setScale(1);
			}
			
			if(!targetting){
				
				float s = 10;
				if(img.getRotation() < 0){
					img.setRotation(MathUtils.lerp(img.getRotation(), 0, delta * s));
				}else{
					img.setRotation(MathUtils.lerp(img.getRotation(), 360, delta * s));
				}
			}else{
				((TextureRegionDrawable)img.getDrawable()).setRegion(GameAssets.i.canonFrames.get(1));
			}
			
		}else{
			shootTime += delta * 3f;
			if(shootTime >= 1){
				shooting = false;
			}
			
			img.setScale(2);
			((TextureRegionDrawable)img.getDrawable()).setRegion(GameAssets.i.canonAnimation.getKeyFrame(shootTime * 4, true));
		}
	}
	
	public void shoot(){
		chargeTime = 0;
		shooting = true;
		shootTime = 0;
		targetting = false;
	}

	public boolean charged() {
		return true; // chargeTime >= 1;
	}
}
