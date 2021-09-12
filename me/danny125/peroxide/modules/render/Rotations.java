package me.danny125.peroxide.modules.render;

import me.danny125.peroxide.Events.Event;
import me.danny125.peroxide.Events.EventUpdate;
import me.danny125.peroxide.Events.MotionEvent;
import me.danny125.peroxide.modules.Module;
import me.danny125.peroxide.settings.ModeSetting;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Rotations extends Module {

	public Rotations() {
		super("Rotations", 0, Category.RENDER);
	}

	public void onEvent(Event e) {
		if(e instanceof MotionEvent) {
			MotionEvent event = (MotionEvent)e;
			
			mc.thePlayer.rotationYawHead = event.getYaw();
			mc.thePlayer.renderYawOffset = event.getYaw();
		
		}
	}
}
