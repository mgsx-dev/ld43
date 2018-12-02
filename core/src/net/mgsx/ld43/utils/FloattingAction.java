package net.mgsx.ld43.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;

public class FloattingAction extends Action
{
	private float time;
	public float amp = 3;

	public FloattingAction() {
	}
	
	@Override
	public boolean act(float delta) {
		time += delta * 7;
		actor.setRotation(15 + MathUtils.lerp(-amp, amp, MathUtils.sin(time) * .5f + .5f));
		return false;
	}
	
	
}
