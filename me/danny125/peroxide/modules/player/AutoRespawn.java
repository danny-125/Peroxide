package me.danny125.peroxide.modules.player;

import me.danny125.peroxide.Events.Event;
import me.danny125.peroxide.Events.EventUpdate;
import me.danny125.peroxide.modules.Module;

public class AutoRespawn extends Module {

	public AutoRespawn() {
		super("AutoRespawn",0,Category.MISC);
	}
	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			if(mc.thePlayer.isDead) {
				mc.thePlayer.respawnPlayer();
			}
		}
	}
}
