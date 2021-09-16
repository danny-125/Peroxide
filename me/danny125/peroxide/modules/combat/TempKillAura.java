package me.danny125.peroxide.modules.combat;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import me.danny125.peroxide.InitClient;
import me.danny125.peroxide.Events.Event;
import me.danny125.peroxide.Events.EventUpdate;
import me.danny125.peroxide.modules.Module;
import me.danny125.peroxide.settings.BooleanSetting;
import me.danny125.peroxide.settings.NumberSetting;
import me.danny125.peroxide.utilities.IsTeammate;
import me.danny125.peroxide.utilities.IsVillager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;

public class TempKillAura extends Module {

	public BooleanSetting team = new BooleanSetting("Team", false);
	public NumberSetting range = new NumberSetting("Range", 5, 1, 6, 1);
	
	public static List<EntityLivingBase> targets;

	public TempKillAura() {
		super("KillAura", 0, Category.COMBAT);
		this.addSettings(range,team);
	}

	public static void click() throws AWTException {
		Robot bot = new Robot();
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

	}

	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
			if (e.isPre()) {

				targets = (List<EntityLivingBase>) Minecraft.getMinecraft().theWorld.loadedEntityList.stream()
						.filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
				targets = targets.stream()
						.filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < range.getValue() && entity != mc.thePlayer)
						.collect(Collectors.toList());
				targets.sort(Comparator
						.comparingDouble(entity -> ((EntityLivingBase) entity).getDistanceToEntity(mc.thePlayer)));

				if (!targets.isEmpty()) {
					EntityLivingBase entity = targets.get(0);
					
						if(InitClient.isModuleToggled("AntiBots")) {
							System.out.println("antibots is toggled");
						if (entity.isInvisible()) {
							System.out.println("cancelled bc of invisible");
							return;
						}
						if (!entity.onGround && entity.motionY == 0 || entity.isAirBorne && entity.motionY == 0) {
							System.out.println("cancelled bc of onground checks");
							return;
						}
						if (entity.getDisplayName().getFormattedText() == "") {
							System.out.println("cancelled bc of name check");
							return;
						}
						}

					if (entity.isDead) {
						return;
					}
					
					if(IsVillager.isVillager(entity)) {
						return;
					}
					
					if ((IsTeammate.isOnSameTeam(entity)) && team.isToggled()) {
						return;
					}

					mc.thePlayer.swingItem();
					mc.getNetHandler().addToSendQueue(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
					mc.playerController.attackEntity(mc.thePlayer, entity);

				}
			}
		}
	}
}
