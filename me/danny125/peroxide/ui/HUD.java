package me.danny125.peroxide.ui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import me.danny125.peroxide.InitClient;
import me.danny125.peroxide.Events.Event;
import me.danny125.peroxide.Events.EventGui;
import me.danny125.peroxide.modules.Module;
import me.danny125.peroxide.modules.Module.Category;
import me.danny125.peroxide.modules.render.ColorModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

import java.awt.Color;
import java.awt.Shape;
import java.util.Comparator;

//changed to a module so someone can toggle it

public class HUD extends Module{
	
	public HUD() {
		super("HUD", Keyboard.KEY_NONE, Category.RENDER);
		this.toggled = true;
	}

	public Minecraft mc = Minecraft.getMinecraft();

	//Lol bad GUI implementation
	
	
	public void onEvent(Event e) {
		if(e instanceof EventGui) {
			
			FontRenderer fr = mc.fontRendererObj;
			
		     InitClient.modules.sort(Comparator.comparingInt(m -> fr.getStringWidth(((Module)m).name)).reversed());
			
			ScaledResolution sr = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
			GuiInventory.drawEntityOnScreen(29, 100, sr.getScaleFactor() * 20, this.mc.thePlayer.rotationYaw, -this.mc.thePlayer.rotationPitch, (EntityLivingBase)this.mc.thePlayer);
		   // GlStateManager.scale(2.0F, 2.0F, 1.0F);
		    InitClient.INSTANCE.customFontBig.drawShadedString(InitClient.clientdisplay, 4d, 4d, InitClient.getColor());
		   // GlStateManager.scale(0.5D, 0.5D, 1.0D);
		    
		    String fps = "FPS: " + String.valueOf(Minecraft.func_175610_ah());
		    InitClient.INSTANCE.customFont.drawShadedString(fps, 4, sr.getScaledHeight() -12, InitClient.getColor());
		    
		    InitClient.INSTANCE.customFont.drawShadedString("Health: " + mc.thePlayer.getHealth(), 4, sr.getScaledHeight() -24, InitClient.getColor());	    
		    
		    int i = 0;
		    for (Module m : InitClient.modules) {
		      if (!m.toggled)
		        continue; 
		      i++;
		      InitClient.INSTANCE.customFont.drawShadedString(m.getModuleName(), (sr.getScaledWidth() - InitClient.INSTANCE.customFont.getStringWidth(m.getModuleName()) - 4), (i * 9 - 4),  InitClient.getColor());
		    } 
		}
	}
}
