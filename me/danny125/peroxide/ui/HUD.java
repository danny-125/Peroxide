package me.danny125.peroxide.ui;

import me.danny125.peroxide.InitClient;
import me.danny125.peroxide.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class HUD {
	public Minecraft mc = Minecraft.getMinecraft();

	public void drawhud() {
	    ScaledResolution sr = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
	    GlStateManager.scale(2.0F, 2.0F, 1.0F);
	    mc.fontRendererObj.drawShadedString(Color.LightPurple + "Peroxide 0.1", 4, 4, -1);
	    GlStateManager.scale(0.5D, 0.5D, 1.0D);
	    
	    String fps = Color.LightPurple + "FPS: " + String.valueOf(Minecraft.func_175610_ah());
	    mc.fontRendererObj.drawShadedString(fps, 4, sr.getScaledHeight() -12, -1);
	    
	    int i = 0;
	    for (Module m : InitClient.modules) {
	      if (!m.toggled)
	        continue; 
	      i++;
	      mc.fontRendererObj.drawShadedString(Color.LightPurple + m.getModuleName(), (sr.getScaledWidth() - this.mc.fontRendererObj.getStringWidth(m.getModuleName()) - 4), (i * 9 - 4), -1);
	    } 
	}
}
