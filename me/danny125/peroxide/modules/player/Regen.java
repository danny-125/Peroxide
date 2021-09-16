package me.danny125.peroxide.modules.player;

import me.danny125.peroxide.Events.Event;
import me.danny125.peroxide.Events.EventUpdate;
import me.danny125.peroxide.Events.MotionEvent;
import me.danny125.peroxide.modules.Module;
import me.danny125.peroxide.settings.ModeSetting;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Regen extends Module {

	public ModeSetting mode = new ModeSetting("Mode", "Mineplex", "Mineplex");
	
	public Regen() {
		super("Regen",0,Category.MISC);
		this.addSettings(mode);
	}

	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			if(e.isPre()) {
				
				// add different modes
				if(mode.getMode() == "Mineplex") {
					if(mc.thePlayer.getHealth() < mc.thePlayer.getMaxHealth()) {
						for (int i = 0; i < 10; i++) {
							mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, true));
						}
					}
				}
				
				
			}
		}
	}
}
