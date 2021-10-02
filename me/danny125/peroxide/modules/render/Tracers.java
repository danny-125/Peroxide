package me.danny125.peroxide.modules.render;

import me.danny125.peroxide.InitClient;
import me.danny125.peroxide.Events.Event;
import me.danny125.peroxide.Events.RenderEvent;
import me.danny125.peroxide.modules.Module;
import me.danny125.peroxide.settings.BooleanSetting;
import me.danny125.peroxide.utilities.render.RenderUtils;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;

public class Tracers extends Module {
	public BooleanSetting player = new BooleanSetting("Player", true);
	public BooleanSetting mob = new BooleanSetting("Mob", true);
	public BooleanSetting passive = new BooleanSetting("Passive", true);
	public BooleanSetting bob = new BooleanSetting("Bob",false);
	
	public Tracers() {
		super("Tracers",0,Category.RENDER);
		this.addSettings(player,mob,passive,bob);
	}

	public void onEvent(Event e) {
		if(e instanceof RenderEvent) {
			if(!bob.isToggled()) {
				mc.gameSettings.viewBobbing = false;
			}else{
				mc.gameSettings.viewBobbing = true;
			}
			
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
					RenderUtils.drawTracerLine(x, y, z, red, green, blue, 0.45F, 1.0F);
				}
				if(entity instanceof EntityAnimal && passive.isToggled() && entity != mc.thePlayer) {
					RenderUtils.drawTracerLine(x, y, z, red, green, blue, 0.45F, 1.0F);
				}
				if(entity instanceof EntityMob && mob.isToggled() && entity != mc.thePlayer) {
					RenderUtils.drawTracerLine(x, y, z, red, green, blue, 0.45F, 1.0F);
				}
			}
		}
	}
}
