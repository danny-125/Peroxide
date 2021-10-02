package me.danny125.peroxide.modules.player;

import me.danny125.peroxide.InitClient;
import me.danny125.peroxide.Events.Event;
import me.danny125.peroxide.Events.EventUpdate;
import me.danny125.peroxide.modules.Module;
import me.danny125.peroxide.settings.ModeSetting;
import me.danny125.peroxide.settings.NumberSetting;
import me.danny125.peroxide.settings.Setting;
import net.minecraft.inventory.ContainerChest;

public class ChestStealer extends Module {
	
	public ChestStealer() {
		super("ChestStealer", 0, Category.MISC);
	}
	
	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
			if (mc.thePlayer.openContainer instanceof ContainerChest) {
				ContainerChest chest = (ContainerChest) mc.thePlayer.openContainer;

				for (int i = 0; i < chest.getLowerChestInventory().getSizeInventory(); i++) {
					mc.playerController.windowClick(chest.windowId, i, 0, 1, mc.thePlayer);
				}
			}
		}
	}

}
