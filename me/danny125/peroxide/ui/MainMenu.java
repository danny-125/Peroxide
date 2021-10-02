package me.danny125.peroxide.ui;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import me.danny125.peroxide.InitClient;
import me.danny125.peroxide.alts.GuiAltManager;
import net.minecraft.client.gui.Gui;
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
        String[] buttons = {"Singleplayer","Multiplayer","Settings","Discord","Alt Manager","Quit"};
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
        
        
        // Title drawer
        InitClient.INSTANCE.customFontHuge.drawCenteredString(InitClient.clientname, sr.getScaledWidth()/2f + 1, sr.getScaledHeight()/2f - 70, new Color(1,1,1));
        InitClient.INSTANCE.customFontHuge.drawCenteredString(InitClient.clientname, sr.getScaledWidth()/2f, sr.getScaledHeight()/2f-70, new Color(255,255,255));
        // Version drawer
        InitClient.INSTANCE.customFont.drawString(InitClient.clientversion, sr.getScaledWidth()/2f + 75, sr.getScaledHeight()/1.93f - 70, new Color(1,1,1));
        InitClient.INSTANCE.customFont.drawString(InitClient.clientversion, sr.getScaledWidth()/2f + 74, sr.getScaledHeight()/1.93f - 70, new Color(255,255,255));
        
        InitClient.INSTANCE.customFont.drawShadedString("Credits: Danny125, OlivesAreShit, Epik123, and Mkeo123", 4, sr.getScaledHeight() -12, new Color(255,255,255));    
        
        
        //String latestversion = "";
        
        //URL url;
		//try {
			//url = new URL("https://raw.githubusercontent.com/danny-125/PeroxideVersion/main/version.txt");
	        //BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
	        //String line;
	        //while ((line = in.readLine()) != null)
	        //  latestversion = line; 
	        //in.close();
		//} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		//}
		String text = "You're running Peroxide Lite, upgrade for more features and more updates!";
		
        //update string
        //if(!InitClient.clientversion.contains(latestversion)) {
        	InitClient.INSTANCE.customFont.drawCenteredString(text, sr.getScaledWidth()/2f, (1 * 9 - 4), new Color(255,255,255));
        //}
    }
    
    public void mouseClicked(int mouseX,int mouseY,int button) {
        ScaledResolution sr = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        
        int count = 0;
        String[] buttons = {"Singleplayer","Multiplayer","Settings","Discord","Alt Manager","Quit"};
        for(String b : buttons) {
            count++;
            
            
            float textlength = b.length() * 5;
            float x = sr.getScaledWidth()/2f;
            float y = (sr.getScaledHeight()/2f - 24) + (count*12);
            
            boolean isHovered = (mouseX + textlength/2f) - x < textlength && (mouseX + textlength/2f) - x > 0 && mouseY - y < 12 && mouseY - y > 0;
            
            if(isHovered) {
            	if(b == "Discord") {
            		URI uri = null;
            		try {
						uri = new URI("https://discord.gg/PVe4MuTCHp");
					} catch (URISyntaxException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
            		try {
						java.awt.Desktop.getDesktop().browse(uri);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            	}
            	if(b == "Alt Manager") {
            		mc.displayGuiScreen(new GuiAltManager());
            	}
                if(b == "Settings") {
                    mc.displayGuiScreen(new GuiOptions(this,mc.gameSettings));
                }
                if(b == "Multiplayer") {
                    mc.displayGuiScreen(new GuiMultiplayer(this));
                }
                if(b == "Singleplayer") {
                    mc.displayGuiScreen(new GuiSelectWorld(this));
                }
                if(b == "Quit") {
                    mc.shutdown();
                }
            }
        }
    }
}