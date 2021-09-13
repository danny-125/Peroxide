package me.danny125.peroxide.modules.combat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.lwjgl.input.Keyboard;

import me.danny125.peroxide.InitClient;
import me.danny125.peroxide.Events.Event;
import me.danny125.peroxide.Events.EventUpdate;
import me.danny125.peroxide.Events.MotionEvent;
import me.danny125.peroxide.modules.Module;
import me.danny125.peroxide.utilities.IsTeammate;
import me.danny125.peroxide.utilities.IsVillager;
import me.danny125.peroxide.utilities.rotation.RotationUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;

public class AntiBots extends Module {

	
	public static List<EntityLivingBase> targets;
	
	public AntiBots() {
		super("AntiBots", Keyboard.KEY_NONE, Category.COMBAT);
	}
	
	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			for(Object T : mc.theWorld.loadedEntityList) {
				Entity entity = (Entity)T;
				
				//Invisible Check and Name Check
				if(entity.isInvisible()) {
					KillAura.bots.add(entity);
				}
				
				if(!(entity instanceof EntityMob) && !(entity instanceof EntityAnimal)) {
					if(entity.getCustomNameTag() == "") {
						KillAura.bots.add(entity);
					}
				}
				
				//Checks for Odd ground movement, also checks if the server is faking onground, removes bots from most servers, but may trigger in a hvh
        		if(!entity.onGround && entity.motionY == 0 || entity.isAirBorne && entity.motionY == 0) {
        			KillAura.bots.add(entity);
        		}
        		
        		//Checks if entity is a villager
        		if(IsVillager.isVillager(entity)) {
        			KillAura.bots.add(entity);
        		}
		
        		//Checks if player is on the same team
        		if(IsTeammate.isOnSameTeam(entity)) {
        			KillAura.bots.add(entity);
        		}
				
			}
	}
	}
}
