package me.danny125.peroxide.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import me.danny125.peroxide.Events.Event;
import me.danny125.peroxide.settings.KeyBindSetting;
import me.danny125.peroxide.settings.Setting;
import net.minecraft.client.Minecraft;

public class Module {
	public boolean toggled;
	public String name;
	public String prefix;
	public boolean expanded;
	public KeyBindSetting keyCode = new KeyBindSetting(0);
	public Category category;
	
	public List<Setting> settings = new ArrayList<Setting>();
	
	public Minecraft mc = Minecraft.getMinecraft();
	
	public Module(String ModName, int key, Category c) {
		this.name = ModName;
		this.category = c;
		keyCode.setCode(key);
		this.addSettings(keyCode);
		
		//null check
		if(prefix == null) {
			prefix = "";
		}
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
	
	public void addSettings(Setting... settings) {
		this.settings.addAll(Arrays.asList(settings));
		this.settings.sort(Comparator.comparingInt(s -> s == keyCode ? 1 : 0));
	}
	
	public void removeSettings(Setting... settings) {
		this.settings.removeAll(Arrays.asList(settings));
		this.settings.sort(Comparator.comparingInt(s -> s == keyCode ? 1 : 0));
	}
	
	public enum Category{
		COMBAT,
		RENDER,
		MOVEMENT,
		MISC;
	}
	
	public int getKey() {
		return keyCode.code;
	}
	public String getModuleName() {
		return name;
	}
	public Category getCategory() {
		return category;
	}

}
