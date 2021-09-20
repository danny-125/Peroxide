package me.danny125.peroxide.modules.player;

import me.danny125.peroxide.Events.Event;
import me.danny125.peroxide.Events.EventGetPacket;
import me.danny125.peroxide.Events.EventUpdate;
import me.danny125.peroxide.modules.Module;
import me.danny125.peroxide.settings.ModeSetting;
import me.danny125.peroxide.settings.NumberSetting;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public class AntiKnockback extends Module {

	public NumberSetting amount = new NumberSetting("Amount", 25, 1, 100, 1);
	
	public AntiKnockback() {
		super("Velocity", 0, Category.MISC);
		this.settings.add(amount);
	}

	public void onEvent(Event e) {
		if (e instanceof EventGetPacket) {
			EventGetPacket pe = (EventGetPacket) e;
			if (pe.getPacket() instanceof S12PacketEntityVelocity) {
				S12PacketEntityVelocity packet = (S12PacketEntityVelocity) pe.getPacket();
				
				double velocity = amount.getValue() / 100;
				
				packet.motionX = (int)(packet.getMotionX() * velocity);
				packet.motionY = (int)(packet.getMotionY() * velocity);
				packet.motionZ = (int)(packet.getMotionZ() * velocity);
			}
		}
	}
}
