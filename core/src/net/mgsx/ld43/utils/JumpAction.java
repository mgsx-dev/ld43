package net.mgsx.ld43.utils;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;

public class JumpAction extends TemporalAction
{
	private float startX, startY, endX, endY, midY;
	
	public JumpAction(float startX, float startY, float endX, float endY, float jumpOffset, float duration,
			Interpolation interpolation) {
		super(duration, interpolation);
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.midY = jumpOffset + (startY + endY)/2;
	}

	@Override
	protected void update(float percent) 
	{
		float px = MathUtils.lerp(startX, endX, percent);
		float py;
		
		if(percent < .5f){
			py = MathUtils.lerp(startY, midY, Interpolation.pow2Out.apply(percent * 2f));
		}else{
			py = MathUtils.lerp(midY, endY, Interpolation.pow2In.apply((percent - .5f) * 2f));
		}
		
		target.setPosition(px, py);
	}
	
}
