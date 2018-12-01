package net.mgsx.ld43.utils;

import com.badlogic.gdx.scenes.scene2d.Action;

public class ThrowAction extends Action
{
	private float dirX;
	private float dirY;
	private float gravity;
	private float rotationSpeed;

	public ThrowAction(float dirX, float dirY, float gravity, float rotationSpeed) {
		this.dirX = dirX;
		this.dirY = dirY;
		this.gravity = gravity;
		this.rotationSpeed = rotationSpeed;
	}
	
	@Override
	public boolean act(float delta) {
		
		dirY -= gravity * delta;
		actor.setPosition(actor.getX() + dirX * delta, actor.getY() + dirY * delta);
		actor.setRotation(actor.getRotation() + delta * rotationSpeed);
		return false;
	}
	
	
}
