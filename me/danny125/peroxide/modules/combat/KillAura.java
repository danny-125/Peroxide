package me.danny125.peroxide.modules.combat;

import java.util.ArrayList;
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

	public static ArrayList<Entity> bots = new ArrayList<Entity>();
	
	public KillAura() {
		super("KillAura", Keyboard.KEY_R, Category.COMBAT);
	}
	
	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			if(e.isPre()) {
				if(this.toggled) {
				//On event
				List<EntityLivingBase> targets = (List<EntityLivingBase>) mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
				targets = targets.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < 4 && !bots.contains(entity) && entity != mc.thePlayer).collect(Collectors.toList());
				targets.sort(Comparator.comparingDouble(entity -> ((EntityLivingBase)entity).getDistanceToEntity(mc.thePlayer)));
					
					
					
				if(!(targets.isEmpty())) {
					Entity entity = targets.get(0);
					
					//Move this to a Antibot
					//maybe add these to a single line?
					
					//Invisible Check and Name Check
					if(entity.isInvisible() || entity.getCustomNameTag() == "") {
					bots.add(entity);
				        return;
					}
					
					//Checks for Odd ground movement, also checks if the server is faking onground, removes bots from most servers, but may trigger in a hvh
	                		if(!entity.onGround && entity.motionY == 0 || entity.isAirBorne && entity.motionY == 0) {
	                		bots.add(entity);
					return;
	                		}
					
					
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
