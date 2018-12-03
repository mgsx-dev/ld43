package net.mgsx.ld43.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import net.mgsx.ld43.assets.GameAssets;

public class Canon extends ShipPart
{
	public float chargeTime = 1;
	public float frameTime;
	
	public boolean shooting;
	
	private float shootTime;
	
	public boolean targetting;
	private boolean idle;
	
	public void update(float delta){
		
		frameTime += delta;
		
		if(charged()){
			img.setColor(Color.WHITE);
			
		}else{
			img.setColor(Color.GRAY);
		}
		
		if(!shooting){
			
			chargeTime += delta / Rules.CANON_CHARGE_TIME;
			chargeTime = Math.min(1, chargeTime);
			
			if(charged() && !idle){
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
		return chargeTime >= 1;
	}

	public void idle() {
		idle = true;
	}

	public void activate() {
		idle = false;
		chargeTime = 1;
	}
}
