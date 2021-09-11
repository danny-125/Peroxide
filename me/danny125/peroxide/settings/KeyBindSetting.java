package me.danny125.peroxide.settings;

public class KeyBindSetting extends Setting{

	public int code;
	
	public  KeyBindSetting(int code) {
		this.name = "Keybind";
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
}
