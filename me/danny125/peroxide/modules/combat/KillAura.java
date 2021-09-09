package me.danny125.peroxide.modules.combat;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.lwjgl.input.Keyboard;

import me.danny125.peroxide.Events.Event;
import me.danny125.peroxide.Events.EventUpdate;
import me.danny125.peroxide.modules.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class KillAura extends Module {

	public KillAura() {
		super("KillAura", Keyboard.KEY_R, Category.COMBAT);
	}
	
	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			if(e.isPre()) {
				if(this.toggled) {
				//On event
				List<EntityLivingBase> targets = (List<EntityLivingBase>) mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
				targets = targets.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < 4 && entity != mc.thePlayer).collect(Collectors.toList());
				targets.sort(Comparator.comparingDouble(entity -> ((EntityLivingBase)entity).getDistanceToEntity(mc.thePlayer)));
				if(!(targets.isEmpty())) {
					EntityLivingBase entity = targets.get(0);
						if(!entity.isInvisible()) {
							if(!(entity.equals(mc.thePlayer))) {
								mc.thePlayer.swingItem();
								mc.playerController.attackEntity(mc.thePlayer, entity);
							}
						}
					}
				}
			}
		}
	}
}
