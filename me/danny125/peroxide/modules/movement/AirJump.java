package me.danny125.peroxide.modules.movement;

import me.danny125.peroxide.Events.Event;
import me.danny125.peroxide.Events.EventUpdate;
import me.danny125.peroxide.modules.Module;

public class AirJump extends Module {

	public AirJump() {
		super("AirJump",0,Category.MOVEMENT);
		// TODO Auto-generated constructor stub
	}
	
	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			mc.thePlayer.onGround = true;
		}
	}
}
