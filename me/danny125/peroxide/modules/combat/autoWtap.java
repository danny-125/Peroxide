package me.danny125.peroxide.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import me.danny125.peroxide.Events.Event;
import me.danny125.peroxide.Events.MotionEvent;
import me.danny125.peroxide.settings.KeyBindSetting;
import me.danny125.peroxide.settings.Setting;
import net.minecraft.client.Minecraft;

public class AutoWtap extends Module {

	public AutoWtap() {
		super("AutoWtap", 0, Category.COMBAT);
	}
	
	public void onEvent(Event e) {
		if(mc.thePlayer.getDistanceToEntity(entity) <= 4){
              mc.thePlayer.setSprinting(false);
            }
	
}
