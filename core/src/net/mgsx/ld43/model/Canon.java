package net.mgsx.ld43.model;

public class Canon {
	public float chargeTime;
	public float frameTime;
	
	public void update(float delta){
		chargeTime += delta * .3f;
		chargeTime = Math.min(1, chargeTime);
		
		frameTime += delta;
	}
	
	public void shoot(){
		chargeTime = 0;
	}

	public boolean charged() {
		return true; // chargeTime >= 1;
	}
}
