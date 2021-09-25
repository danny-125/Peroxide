package me.danny125.peroxide.ui;

import java.awt.Color;

import me.danny125.peroxide.InitClient;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

public class MainMenu extends GuiScreen {
	public MainMenu() {
		
	}
	public void initGui() {
		
	}
	public void drawScreen(int mouseX,int mouseY,float partialTicks) {
		mc.getTextureManager().bindTexture(new ResourceLocation("peroxide/background.jpg"));
		this.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, this.width, this.height, this.width, this.height);
		
		ScaledResolution sr = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
		
		
		
		int count = 0;
		String[] buttons = {"singleplayer","multiplayer","settings","quit"};
		for(String b : buttons) {
			count++;
			float textlength = b.length() * 5;
			float x = sr.getScaledWidth()/2f;
			float y = (sr.getScaledHeight()/2f - 24) + (count*12);
			
			boolean isHovered = (mouseX + textlength/2f) - x < textlength && (mouseX + textlength/2f) - x > 0 && mouseY - y < 12 && mouseY - y > 0;
			
			boolean hovered = mouseX >= x && mouseY >= y && mouseX < x + InitClient.INSTANCE.customFontBig.getStringWidth(b) && mouseY < y + InitClient.INSTANCE.customFontBig.getHeight();
			
			
			InitClient.INSTANCE.customFontBig.drawCenteredString(b, sr.getScaledWidth()/2f+1, (sr.getScaledHeight()/2f - 23) + (count*12), new Color(1,1,1));
			InitClient.INSTANCE.customFontBig.drawCenteredString(b, sr.getScaledWidth()/2f, (sr.getScaledHeight()/2f - 24) + (count*12), isHovered ? new Color(120,120,120) : new Color(255,255,255));
		}
		

		InitClient.INSTANCE.customFontHuge.drawCenteredString(InitClient.clientdisplay, sr.getScaledWidth()/2f + 1, sr.getScaledHeight()/2f - 35, new Color(1,1,1));
		InitClient.INSTANCE.customFontHuge.drawCenteredString(InitClient.clientdisplay, sr.getScaledWidth()/2f, sr.getScaledHeight()/2f-36, new Color(255,255,255));
		
		InitClient.INSTANCE.customFont.drawShadedString("Peroxide made by danny125#3343", 4, sr.getScaledHeight() -12, new Color(255,255,255));	   
	}
	
	public void mouseClicked(int mouseX,int mouseY,int button) {
		ScaledResolution sr = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
		
		int count = 0;
		String[] buttons = {"singleplayer","multiplayer","settings","quit"};
		for(String b : buttons) {
			count++;
			
			
			float textlength = b.length() * 5;
			float x = sr.getScaledWidth()/2f;
			float y = (sr.getScaledHeight()/2f - 24) + (count*12);
			
			boolean isHovered = (mouseX + textlength/2f) - x < textlength && (mouseX + textlength/2f) - x > 0 && mouseY - y < 12 && mouseY - y > 0;
			
			if(isHovered) {
				if(b == "settings") {
					mc.displayGuiScreen(new GuiOptions(this,mc.gameSettings));
				}
				if(b == "multiplayer") {
					mc.displayGuiScreen(new GuiMultiplayer(this));
				}
				if(b == "singleplayer") {
					mc.displayGuiScreen(new GuiSelectWorld(this));
				}
				if(b == "quit") {
					mc.shutdown();
				}
			}
		}
	}
}
