package me.danny125.peroxide.modules.movement;

import me.danny125.peroxide.Events.Event;
import me.danny125.peroxide.Events.EventUpdate;
import me.danny125.peroxide.Events.MotionEvent;
import me.danny125.peroxide.modules.Module;
import me.danny125.peroxide.settings.ModeSetting;
import me.danny125.peroxide.settings.NumberSetting;

public class Flight extends Module {

	public ModeSetting mode = new ModeSetting("Mode", "Vanilla", "BrokenLens", "Vanilla");
	
	public Flight() {
		super("Flight",0,Category.MOVEMENT);
		this.addSettings(mode);
	}
	
	double ypos;
	
	public void onEnable() {	
		ypos = mc.thePlayer.posY;
		mc.thePlayer.capabilities.allowFlying = true;
		mc.thePlayer.jump();
		mc.thePlayer.capabilities.isFlying = true;
	}
	public void onDisable() {
		mc.thePlayer.capabilities.allowFlying = false;
		mc.thePlayer.capabilities.isFlying = false;
	}

	public void onEvent(Event e) {
		if(e instanceof MotionEvent) {
			MotionEvent event = (MotionEvent) e;
			if(mode.getMode() == "BrokenLens") {
				mc.thePlayer.onGround = true;
				mc.thePlayer.isAirBorne = false;
				event.setY(ypos);
			}
		}
	}
}
