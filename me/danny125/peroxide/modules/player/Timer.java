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
import me.danny125.peroxide.utilities.movement.MovementUtil;
import me.danny125.peroxide.utilities.rotation.RotationUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class Timer extends Module {

	public static NumberSetting timer = new NumberSetting("Timer", 1, 1, 5, 1);


	public Timer() {
		super("Timer", Keyboard.KEY_NONE, Category.COMBAT);
		this.addSettings(radius, space, timer);
	}

	public void onEnable() {
		mc.timer.timerSpeed = (float)timer.getValue();
	}

	public void onDisable() {
		mc.timer.timerSpeed = 1f;
	}
