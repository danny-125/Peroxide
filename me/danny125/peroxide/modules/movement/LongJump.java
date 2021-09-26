package me.danny125.peroxide.modules.movement;

import me.danny125.peroxide.Events.Event;
import me.danny125.peroxide.Events.MotionEvent;
import me.danny125.peroxide.modules.Module;
import me.danny125.peroxide.settings.ModeSetting;
import me.danny125.peroxide.utilities.movement.MovementUtil;

public class LongJump extends Module {

	boolean hasJumped;
	//int wasHurt;
	
	
	public ModeSetting mode = new ModeSetting("Mode", "Mineplex", "Mineplex", "Hypixel");
	
	//to:do code a self damage Hypixel Longjump
	
	public LongJump() {
		super("LongJump", 0, Category.MOVEMENT);
		this.addSettings(mode);
	}
	
	public void onEnable() {
		hasJumped = false;
	}
	
	public void onDisable() {
		hasJumped = false;
	}
	
	public void onEvent(Event e) {
		if(e instanceof MotionEvent) {
			if(mode.getMode() == "Mineplex") {
				if(!hasJumped) {
					if(mc.thePlayer.onGround) {
						if(mc.thePlayer.onGround) {
							mc.thePlayer.motionY = 1;
							
						}
						
						
						if(mc.thePlayer.onGround && hasJumped == true) {
							 toggle();
						 }
						
						if(!mc.thePlayer.onGround) {
							
							
							mc.timer.timerSpeed = 0.8f;
							mc.thePlayer.moveStrafing = 0;
							
							mc.gameSettings.keyBindForward.pressed = true;
							
							if(mc.thePlayer.fallDistance == 0) {
								mc.thePlayer.motionY += 0.015;
							} else {
								//todo add custom fall motion so you can jump higher
								//mc.thePlayer.motionY = -0.7;
							}
							
							if(mc.thePlayer.moveForward != 0 && mc.thePlayer.moveStrafing == 0) {
								MovementUtil.setSpeed(0.50f);
							}
							hasJumped = true;
							mc.gameSettings.keyBindForward.pressed = true;

							}
					}
				}
			}
		}
	}

}
