package me.danny125.peroxide.modules.render;

import org.lwjgl.input.Keyboard;

import me.danny125.peroxide.modules.Module;
import me.danny125.peroxide.ui.GUIMethod;

public class ClickGui extends Module {

	public ClickGui() {
		super("ClickGui", Keyboard.KEY_RSHIFT, Category.RENDER);
	}
	public void onEnable() {
		mc.displayGuiScreen(new GUIMethod());
		toggle();
	}
}
