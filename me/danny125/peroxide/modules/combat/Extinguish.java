package me.danny125.peroxide.modules.combat;

import me.danny125.peroxide.Events.Event;
import me.danny125.peroxide.Events.MotionEvent;
import me.danny125.peroxide.modules.Module;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;

public class Extinguish extends Module {

	public Extinguish() {
		super("Extinguish", 0, Category.COMBAT);
	}

	public void onEvent(Event e) {
		if (e instanceof MotionEvent) {
			MotionEvent event = (MotionEvent) e;
			if (mc.thePlayer.isBurning()) {

				for (int i = 0; i < InventoryPlayer.getHotbarSize(); i++) {

					ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);
					if (itemStack != null && itemStack.getItem().getIdFromItem(itemStack.getItem()) == 326) {
						mc.thePlayer.inventory.currentItem = i;
					}
				}
				if(mc.thePlayer.getHeldItem() == null) {
					return;
				}
				if (mc.thePlayer.getHeldItem().getItem().getIdFromItem(mc.thePlayer.getHeldItem().getItem()) == 326) {
					float oldpitch = mc.thePlayer.rotationPitch;
					mc.thePlayer.rotationPitch = 90f;
					mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem());
					mc.thePlayer.rotationPitch = oldpitch;
				}
			}
		}
	}
}
