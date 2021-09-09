package me.danny125.peroxide.ui;

import java.io.IOException;

import me.danny125.peroxide.InitClient;
import me.danny125.peroxide.modules.Module;
import me.danny125.peroxide.modules.Module.Category;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GUIMethod extends GuiScreen {
	public String getModuleNameById(int id) {
	    int i = 0;
	    for (Module m : InitClient.modules) {
	    	if(m.getModuleName() == "ClickGui")
	    		continue;
	      i++;
	      if(i == id) {
	    	  return m.getModuleName();
	      }
	    } 	
	    return null;
	}
	public int getXLoc(Module m) {
		if(m.getCategory() == Category.COMBAT) {
		return 150;
		}
		if(m.getCategory() == Category.RENDER) {
		return 250;
		}
		if(m.getCategory() == Category.MOVEMENT) {
		return 350;
		}
		if(m.getCategory() == Category.MISC) {
		return 450;
		}
		return 0;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		drawDefaultBackground();
		drawRect(150, 2, 220, 14, 0x80FF64FF);
		mc.fontRendererObj.drawShadedString("Combat", 152, 4, 0xffffffff);

		drawRect(250, 2, 322, 14, 0x80FF64FF);
		mc.fontRendererObj.drawShadedString("Render", 252, 4, 0xffffffff);

		drawRect(350, 2, 432, 14, 0x80FF64FF);
		mc.fontRendererObj.drawShadedString("Movement", 352, 4, 0xffffffff);

		drawRect(450, 2, 506, 14, 0x80FF64FF);
		mc.fontRendererObj.drawShadedString("Misc", 452, 4, 0xffffffff);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	@Override
	public void initGui() {
		int i = 0;
	    int combat = 0;
	    int render = 0;
	    int movement = 0;
	    int misc = 0;
	    for (Module m : InitClient.modules) {
	    	if(m.getModuleName() == "ClickGui")
	    		continue;
	      i++;
	      if(m.getCategory() == Category.COMBAT) {
	    	  combat++;
	    	  buttonList.add(new GuiButton(i, getXLoc(m), combat*14,25 + (m.getModuleName().length() * 3) + m.getModuleName().length(), 12, m.getModuleName()));
	      }
	      if(m.getCategory() == Category.RENDER) {
	    	  render++;
	    	  buttonList.add(new GuiButton(i, getXLoc(m), render*14,25 + (m.getModuleName().length() * 3) + m.getModuleName().length(), 12, m.getModuleName()));
	      }
	      if(m.getCategory() == Category.MOVEMENT) {
	    	  movement++;
	    	  buttonList.add(new GuiButton(i, getXLoc(m), movement*14,25 + (m.getModuleName().length() * 3) + m.getModuleName().length(), 12, m.getModuleName()));
	      }
	      if(m.getCategory() == Category.MISC) {
	    	  misc++;
	    	  buttonList.add(new GuiButton(i, getXLoc(m), misc*14,25 + (m.getModuleName().length() * 3) + m.getModuleName().length(), 12, m.getModuleName()));
	      }
	    } 	
		
	}
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		int i = 0;
		for(Module m : InitClient.modules) {
			if(m.getModuleName() == "ClickGui")
				continue;
			i++;
			if(button.id == i) {
				InitClient.toggleModule(getModuleNameById(i));
			}
		}
		if (button.id == 1) {
			InitClient.toggleModule("KillAura");
		}
	}
}