package net.mgsx.ld43.model;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import net.mgsx.ld43.assets.AudioEngine;

public class ShipPart {
	
	public int damages;
	public boolean exploding;
	public float stuntTime;
	
	public Image img;
	public boolean disabled;
	
	public float baseX, baseY;
	public String name;

	public static ShipPart bullet() {
		ShipPart p = new ShipPart();
		p.damages = 2;
		p.stuntTime = 3;
		return p;
	}
	
	public void restore(){
		img.setPosition(baseX, baseY);
		img.setTouchable(Touchable.enabled);
	}

	public void playImpact() 
	{
		if(exploding){
			AudioEngine.i.playSFX(0);
		}
	}
}
