package me.danny125.peroxide.modules.movement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.lwjgl.input.Keyboard;

import me.danny125.peroxide.Events.Event;
import me.danny125.peroxide.Events.EventUpdate;
import me.danny125.peroxide.modules.Module;
import me.danny125.peroxide.settings.ModeSetting;
import me.danny125.peroxide.settings.NumberSetting;
import me.danny125.peroxide.utilities.IsTeammate;
import me.danny125.peroxide.utilities.IsVillager;
import me.danny125.peroxide.utilities.movement.MovementUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class Speed extends Module {

	public ModeSetting mode = new ModeSetting("Mode", "Mineplex", "Mineplex");
	
	public NumberSetting speed = new NumberSetting("test", 1, 1, 10, 0.1);
	
	public Speed() {
		super("Speed", Keyboard.KEY_NONE, Category.COMBAT);
		this.addSettings(mode, speed);
	}
	
	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			if(e.isPre()) {
				if(mc.thePlayer.motionX != 0 || mc.thePlayer.motionZ != 0) {
					
					mc.thePlayer.setSprinting(true);
					
					if(mc.thePlayer.onGround) {		
						
						mc.thePlayer.motionY = MovementUtil.jumpHeight() + 0.1;
					} else {
						
						if(mc.thePlayer.fallDistance == 0) {
							mc.thePlayer.motionY += 0.013;
						}
						
					MovementUtil.strafe(0.45f);
					
					}
				}
			}
		}
	}
}
