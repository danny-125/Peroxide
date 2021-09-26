package me.danny125.peroxide.modules.render;

import me.danny125.peroxide.InitClient;
import me.danny125.peroxide.Events.Event;
import me.danny125.peroxide.Events.RenderEvent;
import me.danny125.peroxide.modules.Module;
import me.danny125.peroxide.settings.BooleanSetting;
import me.danny125.peroxide.utilities.render.RenderUtils;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;

public class ESP extends Module {
	//Thanks to the BillyBob tutorial for renderutils
	
	public BooleanSetting player = new BooleanSetting("Player", false);
	public BooleanSetting mob = new BooleanSetting("Mob", false);
	public BooleanSetting passive = new BooleanSetting("Passive", false);
	
	public ESP() {
		super("ESP",0,Category.RENDER);
		this.addSettings(player,mob,passive);
	}
	
	public void onEvent(Event e) {
		if(e instanceof RenderEvent) {
			for(Object o : mc.theWorld.loadedEntityList) {
				if(!(o instanceof EntityLivingBase)) {
					continue;
				}
				EntityLivingBase entity = (EntityLivingBase)o;
				float red;
				float green;
				float blue;
				
				if(mc.thePlayer.getDistanceToEntity(entity)>25) {
					red = 0;
					green = 1;
					blue = 0;
				}else {
					red = (float) (1-mc.thePlayer.getDistanceToEntity(entity)*0.04);
					green = (float) (mc.thePlayer.getDistanceToEntity(entity)*0.04);
					blue = 0;
				}
				
				if(entity.isInvisible()) {
					continue;
				}
				
				double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * mc.timer.renderPartialTicks - RenderManager.renderPosX;
				double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * mc.timer.renderPartialTicks - RenderManager.renderPosY;
				double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * mc.timer.renderPartialTicks - RenderManager.renderPosZ;
				
				if(entity instanceof EntityPlayer && player.isToggled() && entity != mc.thePlayer) {
					RenderUtils.drawOutlinedEntityESP(x, y, z, entity.width, entity.height, red, green, blue, 1.0F);
				}
				if(entity instanceof EntityAnimal && passive.isToggled() && entity != mc.thePlayer) {
					RenderUtils.drawOutlinedEntityESP(x, y, z, entity.width, entity.height, red, green, blue, 1.0F);
				}
				if(entity instanceof EntityMob && mob.isToggled() && entity != mc.thePlayer) {
					RenderUtils.drawOutlinedEntityESP(x, y, z, entity.width, entity.height, red, green, blue, 1.0F);
				}
				if(entity == mc.thePlayer && mc.gameSettings.thirdPersonView != 0) {
					RenderUtils.drawOutlinedEntityESP(x, y, z, entity.width, entity.height, red, green, blue, 1.0F);
				}
			}
		}
	}

}
