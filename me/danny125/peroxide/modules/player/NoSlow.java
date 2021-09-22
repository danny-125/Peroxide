package me.danny125.peroxide.modules.player;

import me.danny125.peroxide.Events.Event;
import me.danny125.peroxide.Events.EventUpdate;
import me.danny125.peroxide.modules.Module;
import me.danny125.peroxide.utilities.movement.MovementUtil;

public class NoSlow extends Module {

	public NoSlow() {
		super("NoSlow",0,Category.MISC);
	}

	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			if(mc.gameSettings.isKeyDown(mc.gameSettings.keyBindSprint) && mc.thePlayer.isBlocking()) {
				
				MovementUtil.setSpeed(0.313f);
			}
			if(mc.thePlayer.isBlocking() && !mc.gameSettings.isKeyDown(mc.gameSettings.keyBindSprint)) {
				MovementUtil.setSpeed(0.173f);
			}
		}
	}
}
