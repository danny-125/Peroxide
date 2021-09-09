package me.danny125.peroxide.modules;

import me.danny125.peroxide.Events.Event;
import net.minecraft.client.Minecraft;

public class Module {
	public boolean toggled;
	public String name;
	public int keycode;
	public Category category;
	
	public Minecraft mc = Minecraft.getMinecraft();
	
	public Module(String ModName, int keycode, Category c) {
		this.category = c;
		this.keycode = keycode;
		this.name = ModName;
	}

	public void toggle() {
		toggled = !toggled;
		if(toggled) {
			onEnable();
		}else {
			onDisable();
		}
	}
	public void onEnable() {
		
	}
	public void onDisable() {
		
	}
	public void onEvent(Event e) {
		
	}
	
	public enum Category{
		COMBAT,
		RENDER,
		MOVEMENT,
		MISC;
	}

	public int getKey() {
		return keycode;
	}


}
