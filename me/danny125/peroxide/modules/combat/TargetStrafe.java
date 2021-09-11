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

public class TargetStrafe extends Module {

	public static NumberSetting radius = new NumberSetting("Radius", 1, 1, 5, 1);
	public static BooleanSetting space = new BooleanSetting("Space", false);
	public static NumberSetting timer = new NumberSetting("Timer", 1, 1, 5, 1);
	
	boolean isStrafing;
	
	private double Direction;
	
	boolean changedDirection;
	
	public TargetStrafe() {
		super("TargetStrafe", Keyboard.KEY_NONE, Category.COMBAT);
		this.addSettings(radius, space, timer);
	}
	
	public void onEnable() {
		Direction = 1;
	}
	
	public void onDisable() {
		mc.timer.timerSpeed = 1f;
	}
	
	public void strafe(Event e, final double moveSpeed) {
		
		int dist = (int) mc.thePlayer.getDistanceToEntity(KillAura.targets.get(0));
		
		if(mc.thePlayer.isCollidedHorizontally && !changedDirection) {
			
			if(Direction == 1) {
				Direction = -1;
			} else if(Direction == -1) {
				Direction = 1;
			}
			
			changedDirection = true;
		} else {
			changedDirection = false;
		}
		
		
		if(dist > radius.getValue()) {
    		
    		
			mc.timer.timerSpeed = (float)timer.getValue();
			
		
	 MovementUtil.setMotionWithValues(e, MovementUtil.getBaseMoveSpeed() - 0.0, RotationUtil.getPredictedRotations(KillAura.targets.get(0))[0], 1.0D, Direction);
	 } else {
		 MovementUtil.setMotionWithValues(e, MovementUtil.getBaseMoveSpeed(), RotationUtil.getPredictedRotations(KillAura.targets.get(0))[0], 0.0D, Direction); 
	 }
	}
	
	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			if(canStrafe()) {
			if(KillAura.targets != null) {
				if(!KillAura.targets.isEmpty()) {
					isStrafing = true;
					strafe((EventUpdate)e, MovementUtil.getBaseMoveSpeed());
				} else {
					isStrafing = false;
				}
			} else {
				isStrafing = false;
			}
			}
			
			if(!isStrafing) {
				mc.timer.timerSpeed = 1f;
			}
			
		}
	}
	
	public static boolean canStrafe() {
    	if(space.isToggled()) {
        return (InitClient.INSTANCE.getModule("Speed").toggled  && InitClient.INSTANCE.getModule("KillAura").toggled  && Keyboard.isKeyDown(Keyboard.KEY_SPACE));
    	} else {
    		return (InitClient.INSTANCE.getModule("Speed").toggled && InitClient.INSTANCE.getModule("KillAura").toggled);
    	}
    }
	
	
	
}
