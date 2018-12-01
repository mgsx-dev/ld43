package net.mgsx.ld43.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;

public class BlinkAction extends Action{

	private float time;
	private float speed;

	public BlinkAction(float speed) {
		super();
		this.speed = speed;
	}

	@Override
	public boolean act(float delta) {
		time += delta * speed;
		actor.getColor().set(Color.WHITE).mul(MathUtils.sin(time) * 0.5f + 0.5f);
		actor.setScale(MathUtils.lerp(.9f, 1.2f, MathUtils.sin(time) * 0.5f + 0.5f));
		// actor.getColor().a = 1;
		return false;
	}

	
}
