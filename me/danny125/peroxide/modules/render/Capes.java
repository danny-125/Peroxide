package me.danny125.peroxide.modules.render;

import org.lwjgl.input.Keyboard;

import me.danny125.peroxide.Events.Event;
import me.danny125.peroxide.Events.EventUpdate;
import me.danny125.peroxide.modules.Module;
import me.danny125.peroxide.settings.ModeSetting;
import net.minecraft.client.Minecraft;

public class Capes extends Module {
	public static ModeSetting capeType = new ModeSetting("Cape", "Peroxide", "Peroxide", "Minecon11", "Minecon16", "Cake", "Wings");

	Minecraft mc = Minecraft.getMinecraft();
	
	public Capes() {
		super("Capes", Keyboard.KEY_NONE, Category.RENDER);
		this.addSettings(capeType);
	}
}
