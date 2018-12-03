package net.mgsx.ld43.model;

public class Rules {

	public static final boolean DEBUG_SMALL_SHIP = false;
	
	
	
	public static final int CANON_COUNT = 3;
	public static final float CANON_CHARGE_TIME = 8;
	
	public static final float MIN_SHOOT_TIME = CANON_CHARGE_TIME / CANON_COUNT;
	
	public static final int SHARK_LIFE_BASE = 30; // XXX 30;
	
	public static void configurePart(ShipPart part, String name){
		
		// SPECIAL
		
		if("canon".equals(name))
		{
			part.damages = 1;
			part.stuntTime = 4;
		}
		else if("pirate".equals(name))
		{
			part.damages = 0;
			part.stuntTime = CANON_CHARGE_TIME;
		}
		
		// EXPLODING
		
		else if("baril".equals(name))
		{
			part.damages = 5;
			part.stuntTime = 3;
			part.exploding = true;
		}
		else if("bottle".equals(name))
		{
			part.damages = 5;
			part.stuntTime = 3;
			part.exploding = true;
		}
		
		// OTHERS...
		
		else if("anchor".equals(name))
		{
			part.damages = 3;
			part.stuntTime = CANON_CHARGE_TIME;
		}
		
		else if("crochet".equals(name))
		{
			part.damages = 3;
			part.stuntTime = CANON_CHARGE_TIME;
		}

		
		else if("sword".equals(name))
		{
			part.damages = 5;
			part.stuntTime = 0;
		}

		else
		{
			part.damages = 1;
			part.stuntTime = 4;
		}
	}
}
