package me.danny125.peroxide.settings;

public class BooleanSetting extends Setting{
	
	boolean toggled;
	
	public BooleanSetting(String name, boolean toggled) {
		this.name = name;
		this.toggled = toggled;
		
	}

	public boolean isToggled() {
		return toggled;
	}

	public void toggle() {
		toggled = !toggled;
	}
	
	public void setToggled(boolean toggled) {
		this.toggled = toggled;
	}
	
}
