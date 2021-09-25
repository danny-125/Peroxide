package me.danny125.peroxide.modules.render;

import me.danny125.peroxide.InitClient;
import me.danny125.peroxide.modules.Module;
import me.danny125.peroxide.settings.NumberSetting;

public class ColorModule extends Module {

	public NumberSetting red = new NumberSetting("Red", 191, 1, 255, 1);
	public NumberSetting green = new NumberSetting("Green", 11, 1, 255, 1);
	public NumberSetting blue = new NumberSetting("Blue", 255, 1, 255, 1);
	
	public ColorModule() {
		super("Color",0,Category.RENDER);
		this.addSettings(red,green,blue);
	}
}
