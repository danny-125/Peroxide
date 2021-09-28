package me.danny125.peroxide.modules.movement;

import me.danny125.peroxide.Events.Event;
import me.danny125.peroxide.Events.EventUpdate;
import me.danny125.peroxide.modules.Module;
import me.danny125.peroxide.settings.BooleanSetting;

public class AutoSprint extends Module {
	
	public BooleanSetting omni = new BooleanSetting("Omni", false);

	public AutoSprint() {
		super("AutoSprint", 0, Category.MOVEMENT);
		this.addSettings(omni);
	}
	
	public void onEvent(Event e) {
		if(e.isPre()) {
			if(e instanceof EventUpdate) {
				// credits to OlivesAreShit for these checks
				if(omni.isToggled()) {
					mc.thePlayer.setSprinting(true);
				}else
					if(mc.thePlayer.moveForward > 0 && !mc.thePlayer.isBlocking() && !mc.thePlayer.isUsingItem() && !mc.thePlayer.isOnLadder() && !mc.thePlayer.isEating() && !mc.thePlayer.isSneaking()) {
						mc.thePlayer.setSprinting(true);
			}
			}
		}
	}

}
