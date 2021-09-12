package me.danny125.peroxide.modules.combat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.lwjgl.input.Keyboard;

import me.danny125.peroxide.InitClient;
import me.danny125.peroxide.Events.Event;
import me.danny125.peroxide.Events.EventUpdate;
import me.danny125.peroxide.modules.Module;
import me.danny125.peroxide.utilities.IsTeammate;
import me.danny125.peroxide.utilities.IsVillager;
import me.danny125.peroxide.utilities.rotation.RotationUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;

public class KillAura extends Module {

	public static ArrayList<Entity> bots = new ArrayList<Entity>();
	
	
	public static List<EntityLivingBase> targets;
	
	public KillAura() {
		super("KillAura", Keyboard.KEY_R, Category.COMBAT);
	}
	
	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			if(e.isPre()) {
				if(this.toggled) {
				//On event
				targets = (List<EntityLivingBase>) mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
				targets = targets.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < 4 && !bots.contains(entity) && entity != mc.thePlayer).collect(Collectors.toList());
				targets.sort(Comparator.comparingDouble(entity -> ((EntityLivingBase)entity).getDistanceToEntity(mc.thePlayer)));
					
					
					
				if(!(targets.isEmpty())) {
					Entity entity = targets.get(0);

					
					//Move this to a Antibot
					//maybe add these to a single line?
					
					//Invisible Check and Name Check
					if(entity.isInvisible()) {
				        return;
					}
					
					if(entity instanceof EntityPlayer) {
						if(entity.getCustomNameTag() == "") {
							bots.add(entity);
							return;
						}
					}
					
					//Checks for Odd ground movement, also checks if the server is faking onground, removes bots from most servers, but may trigger in a hvh
	                		if(!entity.onGround && entity.motionY == 0 || entity.isAirBorne && entity.motionY == 0) {
	                		bots.add(entity);
					return;
	                		}
	                		//Checks if entity is a villager
	                		if(IsVillager.isVillager(entity)) {
	                			return;
	                		}
					
	                		//Checks if player is on the same team
	                		if(IsTeammate.isOnSameTeam(entity)) {
	                			return;
	                		}
					
					
							if(!(entity.equals(mc.thePlayer))) {
								
								mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, RotationUtil.getPredictedRotations((EntityLivingBase) entity)[0], RotationUtil.getPredictedRotations((EntityLivingBase) entity)[1], mc.thePlayer.onGround));
								
								mc.thePlayer.swingItem();
								mc.playerController.attackEntity(mc.thePlayer, entity);
							}
					
				}
			}
		}
	}
	}
}
