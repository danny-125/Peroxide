package me.danny125.peroxide.modules.player;

import me.danny125.peroxide.Events.Event;
import me.danny125.peroxide.modules.Module;
import net.minecraft.network.play.client.C03PacketPlayer;

public class NoFall extends Module {

	public NoFall() {
		super("NoFall", 0, Category.MISC);
	}

	public void onEvent(Event e) {
		if(e.isPre()) {
			if (mc.thePlayer.fallDistance > 2F) {
				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
			}
		}
	}
}
