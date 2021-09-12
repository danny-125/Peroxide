package me.danny125.peroxide.modules.player;

import me.danny125.peroxide.Events.Event;
import me.danny125.peroxide.Events.EventGetPacket;
import me.danny125.peroxide.Events.EventUpdate;
import me.danny125.peroxide.modules.Module;
import me.danny125.peroxide.settings.ModeSetting;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public class Velocity extends Module {

	public ModeSetting mode = new ModeSetting("Mode", "Packet", "GroundSpoof", "Packet");
	
	public Velocity() {
		super("Velocity", 0, Category.MISC);
		this.addSettings(mode);
	}

	public void onEvent(Event e) {
		
		if(e instanceof EventUpdate) {
			if(mode.getMode() == "GroundSpoof") {
			mc.thePlayer.onGround = true;
			}
		}
		
		if(e instanceof EventGetPacket) {
		EventGetPacket event = (EventGetPacket)e;
		
		if(mode.getMode() == "Packet") {
		//change packet values to reduce it instead of cancelling it entirely
		if(event.getPacket() instanceof S12PacketEntityVelocity) {
			
			S12PacketEntityVelocity packet = (S12PacketEntityVelocity)event.getPacket();
			
			
				event.setCancelled(true);
				
			}
		if(event.getPacket() instanceof S27PacketExplosion) {
			
			S27PacketExplosion packet = (S27PacketExplosion)event.getPacket();
			
			
				event.setCancelled(true);
				
			}
		}
		}
	}
}
