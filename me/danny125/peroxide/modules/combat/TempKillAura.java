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
import me.danny125.peroxide.Events.MotionEvent;
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
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class TempKillAura extends Module {
	public BooleanSetting team = new BooleanSetting("Team", false);
	public NumberSetting range = new NumberSetting("Range", 4, 1, 6, 1);
	public NumberSetting aps = new NumberSetting("APS", 8, 1, 15, 1);

	public static List<EntityLivingBase> targets;

	public float[] facing;

	public TempKillAura() {
		super("KillAura", 0, Category.COMBAT);
		this.addSettings(range,aps,team);
	}

	public long lastMS = System.currentTimeMillis();

	public void reset() {
		this.lastMS = System.currentTimeMillis();
	}

	public boolean hasTimeElapsed(long time, boolean reset) {
		if (System.currentTimeMillis() - this.lastMS > time) {
			if (reset) {
				reset();
			}
			return true;
		}

		return false;
	}

	public void setTime(long Time) {
		this.lastMS = Time;
	}

	public long getTime() {
		return System.currentTimeMillis() - this.lastMS;
	}

	public void onEvent(Event e) {
		if (e instanceof MotionEvent) {
			MotionEvent event = (MotionEvent) e;

			targets = (List<EntityLivingBase>) Minecraft.getMinecraft().theWorld.loadedEntityList.stream()
					.filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
			targets = targets.stream()
					.filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < range.getValue() && entity != mc.thePlayer)
					.collect(Collectors.toList());
			targets.sort(Comparator
					.comparingDouble(entity -> ((EntityLivingBase) entity).getDistanceToEntity(mc.thePlayer)));

			if (!targets.isEmpty()) {
				EntityLivingBase entity = targets.get(0);

				AxisAlignedBB bb = entity.getEntityBoundingBox();

				if (e.isPre()) {

					double posx = mc.thePlayer.posX;
					double posy = mc.thePlayer.posY;
					double posz = mc.thePlayer.posZ;
					double eyeheight = mc.thePlayer.getEyeHeight();

					Vec3 eyes = new Vec3(posx, posy + eyeheight, posz);
					Vec3 vector = new Vec3(bb.minX + (bb.maxX - bb.minX) * 0.8 * Math.random(),
							bb.minY + (bb.maxY - bb.minY) * 1 * Math.random(),
							bb.minZ + (bb.maxZ - bb.minZ) * 0.8 * Math.random());
					float yaw = (float) Math
							.toDegrees(Math.atan2(vector.zCoord - eyes.zCoord, vector.xCoord - eyes.xCoord)) - 90.0f;
					float pitch = (float) (-Math.toDegrees(Math.atan2(vector.yCoord - eyes.yCoord,
							Math.sqrt(vector.xCoord - eyes.xCoord * vector.xCoord - eyes.xCoord + vector.zCoord
									- eyes.zCoord * vector.zCoord - eyes.zCoord))));

					facing = new float[] { MathHelper.wrapAngleTo180_float(yaw),
							MathHelper.wrapAngleTo180_float(pitch) };

					event.setYaw(facing[0]);
					event.setPitch(facing[1]);
				}
				if (e.isPost()) {

					if (InitClient.isModuleToggled("AntiBots")) {
						if (entity.isInvisible()) {
							return;
						}
						if (!entity.onGround && entity.motionY == 0 || entity.isAirBorne && entity.motionY == 0) {
							return;
						}
						if (entity.getDisplayName().getFormattedText() == "") {
							return;
						}
					}

					if (entity.isDead) {
						return;
					}

					if (IsVillager.isVillager(entity)) {
						return;
					}

					if ((IsTeammate.isOnSameTeam(entity)) && !team.isToggled()) {
						return;
					}
					
					if (hasTimeElapsed(1000 / Math.round(aps.getValue()), true)) {
						mc.thePlayer.swingItem();
						mc.getNetHandler()
								.addToSendQueue(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
						mc.playerController.attackEntity(mc.thePlayer, entity);
					}
				}
			}

		}
	}
}
