package me.danny125.peroxide.settings;

import java.util.Arrays;
import java.util.List;

public class ModeSetting extends Setting{
	
	public int index;
	
	public List<String>modes;
	
	public ModeSetting(String name, String mode, String... modes) {
		this.name = name;
		this.modes = Arrays.asList(modes);
		index = this.modes.indexOf(mode);
	}
	
	public void cycle() {
		if(index < modes.size() - 1) {
			index++;
		}else {
			index = 0;
		}
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean is(String mode) {
		return index == modes.indexOf(mode);
	}
	
	public String getMode() {
		return modes.get(index);
	}
	
	public List<String> getModes() {
		return modes;
	}

	public void setModes(List<String> modes) {
		this.modes = modes;
	}
}
