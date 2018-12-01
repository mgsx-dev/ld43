package net.mgsx.ld43.utils;

import com.badlogic.gdx.scenes.scene2d.Action;

public class WaterAction extends Action
{
	private boolean started;
	private float baseX;
	private float speed = 500;
	
	
	
	public WaterAction(float speed) {
		super();
		this.speed = speed * 500;
	}
	@Override
	public void reset() {
		started = false;
		super.reset();
	}
	@Override
	public boolean act(float delta) {
		if(!started){
			started = true;
			baseX = actor.getX();
		}
		actor.setX(actor.getX() - delta * speed);
		if(actor.getX() < baseX - actor.getWidth()){
			actor.setX(actor.getX() + actor.getWidth());
		}
		return false;
	}
}
