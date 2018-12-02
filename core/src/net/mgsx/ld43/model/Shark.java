package net.mgsx.ld43.model;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import net.mgsx.ld43.assets.GameAssets;
import net.mgsx.ld43.utils.FloattingAction;

public class Shark {
	private float sharkTime;
	private Image imgShark;
	public Circle circleShaper = new Circle();
	
	private float hMotion;
	
	public boolean stunt;
	
	private float stuntTime;
	
	private float offsetX;
	public boolean attacking;
	private float attackTime;
	
	public float sharkLifeMax = 30;
	public float sharkLife = sharkLifeMax;
	public float sharkLifeSmooth;

	public Image create(){
		imgShark = new Image();
		
		imgShark = new Image(new TextureRegionDrawable(GameAssets.i.sharkFrames.first()));
		imgShark.setTouchable(Touchable.disabled);
		imgShark.setOrigin(Align.center);
		imgShark.addAction(new FloattingAction());
		
		imgShark.setUserObject(this);
		
		return imgShark;
	}
	
	public void hurted(ShipPart part){
		
		// TODO
		stunt = true;
		
		stuntTime = 2; // TODO depends on part
		
		sharkLife -= part.damages;
	}
	
	public void update(float delta)
	{
		sharkLifeSmooth = MathUtils.lerp(sharkLifeSmooth, sharkLife, delta * .3f);
		
		sharkTime += delta * 10;
		
		// imgShark.setDrawable(new D);
		
		circleShaper.set(imgShark.getX() + 780,  imgShark.getY() + 180, 160);

		if(stunt){
			setFrame(0);
			offsetX -= delta * 300;
			stuntTime -= delta;
			if(stuntTime < 0){
				stunt = false;
			}
		}else if(attacking){
			attackTime += delta * 4;
			if(attackTime > 1){
				attacking = false;
			}
			attackAnim();
			offsetX = MathUtils.lerp(offsetX, -200, delta);
		}else{
			offsetX = MathUtils.lerp(offsetX, 0, delta);
			setFrame(2);
			hMotion += delta * 4.5f; // XXX .5f
		}
		imgShark.setX(offsetX + MathUtils.lerp(
				-100, 
				400 + 300, // XXX debug + 300 
				MathUtils.sin(hMotion)));
		
		
//		float attackTime = sharkTime * .2f;
//		if(attackTime % 4f > 2f){
//			imgShark.setPosition(MathUtils.lerp(0, 500, MathUtils.sin(attackTime)), 0);
//		}else{
//			imgShark.setPosition(MathUtils.lerp(0, 10, MathUtils.sin(attackTime)), 0);
//		}
	}
	
	private void setFrame(int i){
		((TextureRegionDrawable)imgShark.getDrawable()).setRegion(GameAssets.i.sharkFrames.get(i));
	}
	
	private void attackAnim(){
		((TextureRegionDrawable)imgShark.getDrawable()).setRegion(GameAssets.i.sharkAnimation.getKeyFrame(sharkTime, true));
	}

	public void attack() {
		attacking = true;
		attackTime = 0f;
	}
}
