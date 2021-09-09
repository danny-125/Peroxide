package me.danny125.peroxide.modules.render;

import org.lwjgl.input.Keyboard;

import me.danny125.peroxide.Events.Event;
import me.danny125.peroxide.Events.EventUpdate;
import me.danny125.peroxide.modules.Module;
import net.minecraft.client.Minecraft;

public class Fullbright extends Module {

	Minecraft mc = Minecraft.getMinecraft();
	
	public Fullbright() {
		super("Fullbright", Keyboard.KEY_U, Category.RENDER);
	}
	
	public void onEnable() {
		mc.gameSettings.gammaSetting = 10f;
	}
	public void onDisable() {
		mc.gameSettings.gammaSetting = 1f;
	}
	
	public void onEvent(Event e) {
		if(e instanceof EventUpdate){
			if(e.isPre()) {
				
			}
		}
	}
}
