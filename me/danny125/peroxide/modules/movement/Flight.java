package me.danny125.peroxide.modules.movement;

import me.danny125.peroxide.Events.Event;
import me.danny125.peroxide.Events.EventUpdate;
import me.danny125.peroxide.Events.MotionEvent;
import me.danny125.peroxide.modules.Module;
import me.danny125.peroxide.settings.ModeSetting;
import me.danny125.peroxide.settings.NumberSetting;
import me.danny125.peroxide.utilities.movement.MovementUtil;

public class Flight extends Module {

	public ModeSetting mode = new ModeSetting("Mode", "Vanilla", "Brlns", "Vanilla");

	public Flight() {
		super("Flight", 0, Category.MOVEMENT);
		this.addSettings(mode);
	}

	public void onEnable() {
		if (mode.getMode() == "Brlns") {
			mc.thePlayer.jump();
		}
		if (mode.getMode() == "Vanilla") {
			mc.thePlayer.capabilities.allowFlying = true;
			mc.thePlayer.jump();
			mc.thePlayer.capabilities.isFlying = true;
		}
	}

	public void onDisable() {
		if (mode.getMode() == "Vanilla") {
			mc.thePlayer.capabilities.allowFlying = false;
			mc.thePlayer.capabilities.isFlying = false;
		}
	}

	public void onEvent(Event e) {
		if (e instanceof MotionEvent) {
			MotionEvent event = (MotionEvent) e;
			if (mode.getMode() == "Brlns") {
				event.onGround = true;
				MovementUtil.setSpeed(0.5);
				if (mc.gameSettings.isKeyDown(mc.gameSettings.keyBindJump)) {
					mc.thePlayer.motionY = 0.5f;
				} else {
					if (mc.gameSettings.isKeyDown(mc.gameSettings.keyBindSneak)) {
						mc.thePlayer.motionY = -0.5f;
					} else {
						mc.thePlayer.motionY = 0;
					}
				}

				mc.timer.timerSpeed = 1f;

			}
		}
	}
}
