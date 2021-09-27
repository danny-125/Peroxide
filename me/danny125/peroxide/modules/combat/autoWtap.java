package me.danny125.peroxide.modules.combat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import me.danny125.peroxide.Events.Event;
import me.danny125.peroxide.Events.MotionEvent;
import me.danny125.peroxide.modules.Module;
import me.danny125.peroxide.settings.KeyBindSetting;
import me.danny125.peroxide.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class AutoWtap extends Module {

	public AutoWtap() {
		super("W-Tap", 0, Category.COMBAT);
	}
	
	public void onEvent(Event e) {
		for(Object o : mc.theWorld.loadedEntityList) {
			if(!(o instanceof EntityLivingBase)) {
				continue;
			}
			EntityLivingBase entity = (EntityLivingBase)o;
			
			if(entity == mc.thePlayer) {
				continue;
			}

	
		if(mc.thePlayer.getDistanceToEntity(entity) <= 4 && mc.thePlayer.isSprinting()){
              mc.thePlayer.setSprinting(false);
            }
		}
	}
}
