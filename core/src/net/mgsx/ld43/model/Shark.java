package net.mgsx.ld43.model;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import net.mgsx.ld43.assets.AudioEngine;
import net.mgsx.ld43.assets.GameAssets;
import net.mgsx.ld43.utils.FloattingAction;

public class Shark {
	private float sharkTime;
	private Image imgShark;
	public Circle circleShaper = new Circle();
	
	private float hMotion;
	
	public boolean stunt;
	
	private float stuntTime;
	
	private float offsetX = -2000;
	private float offsetY;
	
	public boolean attacking;
	private float attackTime;
	
	public float sharkLifeMax = Rules.SHARK_LIFE_BASE;
	public float sharkLife = sharkLifeMax;
	public float sharkLifeSmooth;
	
	private float preRun = 3f;
	private float hMotionSpeed;
	private boolean leave;
	private boolean eatHuman;
	
	public Shark(int level) {
		hMotionSpeed = level * .5f;
	}

	public Image create(){
		imgShark = new Image();
		
		imgShark = new Image(new TextureRegionDrawable(GameAssets.i.sharkFrames.get(0)));
		imgShark.setTouchable(Touchable.disabled);
		imgShark.setOrigin(Align.center);
		imgShark.addAction(new FloattingAction());
		
		imgShark.setUserObject(this);
		
		return imgShark;
	}
	
	public void hurted(ShipPart part){
		
		stunt = true;
		
		stuntTime = part.stuntTime;
		
		sharkLife -= part.damages;
		
		if("pirate".equals(part.name)){
			AudioEngine.i.playSFX(12);
		}
	}
	
	public void leave(){
		
		leave = true;
	}
	
	public void update(float delta)
	{
		preRun -= delta;
		
		sharkLifeSmooth = MathUtils.lerp(sharkLifeSmooth, sharkLife, delta * 3f);
		
		sharkTime += delta * 10;
		
		// imgShark.setDrawable(new D);
		
		circleShaper.set(imgShark.getX() + 780,  imgShark.getY() + 180, 160);

		if(leave){
			offsetX += delta * 900;
			setFrame(2);
		}
		else if(sharkLife <= 0){
			imgShark.clearActions();
			setFrame(5);
			offsetX -= delta * 500;
			offsetY -= delta * 100;
		}
		else if(stunt){
			setFrame(5);
			offsetX -= delta * 300;
			stuntTime -= delta;
			if(stuntTime < 0){
				stunt = false;
				eatHuman = false;
			}
			if(eatHuman){
				animEatHuman();
			}
		}else if(attacking){
			attackTime += delta * 4;
			if(attackTime > 1){
				attacking = false;
			}
			attackAnim();
			offsetX = MathUtils.lerp(offsetX, -200, delta);
		}else if(preRun > 0){
			setFrame(2);
			offsetX = MathUtils.lerp(offsetX, 0, delta);
		}else{
			offsetX = MathUtils.lerp(offsetX, 0, delta);
			setFrame(2);
			hMotion += delta * hMotionSpeed;
		}
		
		float hShark = MathUtils.lerp( -100,  300,  MathUtils.sin(hMotion));
		// TODO add other sins
		
		hShark += MathUtils.lerp( 0,  200,  MathUtils.sin(hMotion * .5f + 0.72f)*.5f+.5f);
		hShark += MathUtils.lerp( 0,  100,  MathUtils.sin(hMotion * .25f + 1.2f)*.5f+.5f);
		
		
		
		
		imgShark.setX(offsetX + hShark);
		
		imgShark.setY(offsetY);
		
		
//		float attackTime = sharkTime * .2f;
//		if(attackTime % 4f > 2f){
//			imgShark.setPosition(MathUtils.lerp(0, 500, MathUtils.sin(attackTime)), 0);
//		}else{
//			imgShark.setPosition(MathUtils.lerp(0, 10, MathUtils.sin(attackTime)), 0);
//		}
	}
	
	private void animEatHuman() {
		// setFrame(2); // TODO eat !
		((TextureRegionDrawable)imgShark.getDrawable()).setRegion(GameAssets.i.sharkAnimationEat.getKeyFrame(sharkTime * .5f, true));
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

	public void eatPirate() {
		// TODO set animation
		
		eatHuman = true;
		
	}
}
