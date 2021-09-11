package me.danny125.peroxide.modules.movement;

import me.danny125.peroxide.Events.Event;
import me.danny125.peroxide.modules.Module;

public class AutoSprint extends Module {

	public AutoSprint() {
		super("AutoSprint", 0, Category.MOVEMENT);
	}
	
	public void onEvent(Event e) {
		if(e.isPre()) {
			if(mc.thePlayer.moveForward > 0f && !mc.thePlayer.isSneaking()) {
				mc.thePlayer.setSprinting(true);
			}
		}
	}

}
