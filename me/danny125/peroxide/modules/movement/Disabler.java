package me.danny125.peroxide.modules.movement;

import me.danny125.peroxide.Events.Event;
import me.danny125.peroxide.Events.EventGetPacket;
import me.danny125.peroxide.Events.EventUpdate;
import me.danny125.peroxide.modules.Module;
import me.danny125.peroxide.settings.ModeSetting;
import me.danny125.peroxide.utilities.RandomUtil;
import net.minecraft.network.play.client.C00PacketKeepAlive;

public class Disabler extends Module {

	public ModeSetting mode = new ModeSetting("Mode", "Mineplex", "Mineplex", "Hypixel");

	public Disabler() {
		super("Disabler", 0, Category.MISC);
	}

	public void onEvent(Event e) {
		if (mode.getMode() == "Mineplex") {
			if (e instanceof EventGetPacket) {
				EventGetPacket event = (EventGetPacket) e;

				if (event.getPacket() instanceof C00PacketKeepAlive) {
					((C00PacketKeepAlive) event.getPacket()).key -= RandomUtil.randomNumber(2.147483647E9D, 1000.0D);
				}
			}
		}
	}

}
