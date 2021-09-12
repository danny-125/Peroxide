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
import me.danny125.peroxide.settings.BooleanSetting;
import me.danny125.peroxide.settings.NumberSetting;
import me.danny125.peroxide.utilities.IsTeammate;
import me.danny125.peroxide.utilities.IsVillager;
import me.danny125.peroxide.utilities.rotation.RotationUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;

public class KillAura extends Module {

	public static ArrayList<Entity> bots = new ArrayList<Entity>();
	
	
	public static List<EntityLivingBase> targets;
	
	
	public NumberSetting range = new NumberSetting("Range", 5, 1, 6, 1);
	public BooleanSetting autoblock = new BooleanSetting("AutoBlock", true);
	public BooleanSetting players = new BooleanSetting("Players", true);
	public BooleanSetting mobs = new BooleanSetting("Mobs", true);
	public BooleanSetting animals = new BooleanSetting("Players", true);
	public BooleanSetting dead = new BooleanSetting("Dead", true);
	
	public KillAura() {
		super("KillAura", Keyboard.KEY_NONE, Category.COMBAT);
		this.addSettings(range, players, mobs,animals,dead);
	}
	
	public void onEvent(Event e) {
		if(e instanceof MotionEvent) {
			
			MotionEvent event = (MotionEvent)e;
			
			if(e.isPre()) {
				if(this.toggled) {
				//On event
				targets = (List<EntityLivingBase>) mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
				targets = targets.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < range.getValue() && !bots.contains(entity) && entity != mc.thePlayer).collect(Collectors.toList());
				targets.sort(Comparator.comparingDouble(entity -> ((EntityLivingBase)entity).getDistanceToEntity(mc.thePlayer)));
					
				//more sorting shit
				if(!players.isToggled()) {
					targets = targets.stream().filter(entity -> !(entity instanceof EntityPlayer)).collect(Collectors.toList());
				}
				
				if(!mobs.isToggled()) {
					targets = targets.stream().filter(entity -> !(entity instanceof EntityMob)).collect(Collectors.toList());
				}
				
				if(!animals.isToggled()) {
					targets = targets.stream().filter(entity -> !(entity instanceof EntityAnimal)).collect(Collectors.toList());
				}
				
				if(!dead.isToggled()) {
					targets = targets.stream().filter(entity -> entity.isEntityAlive()).collect(Collectors.toList());
				}
				
				
					
				if(!(targets.isEmpty())) {
					Entity entity = targets.get(0);
					
					
							if(!(entity.equals(mc.thePlayer))) {
								
								event.yaw = RotationUtil.getPredictedRotations((EntityLivingBase) entity)[0];
								event.pitch = RotationUtil.getPredictedRotations((EntityLivingBase) entity)[1];
								
								//additional range check so it doesn't flag decent anticheat's
								if(mc.thePlayer.getDistanceToEntity(entity) > range.getValue())
									return;
								
								mc.thePlayer.swingItem();
								mc.getNetHandler().addToSendQueue(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
								mc.playerController.attackEntity(mc.thePlayer, entity);
								
								
								if(autoblock.isToggled()) {
									mc.gameSettings.keyBindUseItem.pressed = true;
									mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem());
								}
							}
					
				}
			}
		}
	}
	}
}
