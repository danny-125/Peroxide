package me.danny125.peroxide.modules.player;

import me.danny125.peroxide.Events.Event;
import me.danny125.peroxide.Events.EventUpdate;
import me.danny125.peroxide.modules.Module;
import me.danny125.peroxide.settings.ModeSetting;
import net.minecraft.network.play.client.C03PacketPlayer;

public class NoFall extends Module {

	public ModeSetting mode = new ModeSetting("Mode", "Packet", "GroundSpoof", "Packet");
	
	public NoFall() {
		super("NoFall", 0, Category.MISC);
		this.addSettings(mode);
	}

	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
		if(e.isPre()) {
			if(mode.getMode().equals("Packet")) { 
			if (mc.thePlayer.fallDistance > 2F) {
				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
			}
			}
			
			if(mode.getMode().equals("GroundSpoof")) {
				mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, true));
			}
		}
		}
	}
}
