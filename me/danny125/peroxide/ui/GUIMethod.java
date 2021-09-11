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
	public int getSuitedColor(Module m) {
		if(!m.toggled) {
			return 0x80000000;
		}else {
			return 0x80FF64FF;
		}
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
	public int getXLoc2(Module m) {
		if(m.getCategory() == Category.COMBAT) {
		return 220;
		}
		if(m.getCategory() == Category.RENDER) {
		return 320;
		}
		if(m.getCategory() == Category.MOVEMENT) {
		return 420;
		}
		if(m.getCategory() == Category.MISC) {
		return 520;
		}
		return 0;
	}
	public int getYLoc(Module m) {
		 int combat = 0;
		    int render = 0;
		    int movement = 0;
		    int misc = 0;
		    for (Module m1 : InitClient.modules) {
		    	
		      if(m1.getModuleName() == "ClickGui")
		    	continue;
		      
		      if(m1.getCategory() == Category.COMBAT) {
		    	  combat++;
		    	 if(m.getModuleName() == m1.getModuleName()) {
		    		 return 14*combat;
		    	 }
		      }
		      if(m1.getCategory() == Category.RENDER) {
		    	  render++;
			    	 if(m.getModuleName() == m1.getModuleName()) {
			    		 return 14*render;
			    	 }
		      }
		      if(m1.getCategory() == Category.MOVEMENT) {
		    	  movement++;
			    	 if(m.getModuleName() == m1.getModuleName()) {
			    		 return 14*movement;
			    	 }
		      }
		      if(m1.getCategory() == Category.MISC) {
		    	  misc++;
			    	 if(m.getModuleName() == m1.getModuleName()) {
			    		 return 14*misc;
			    	 }
		      }
		    }
		    return 0;
	}
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		drawDefaultBackground();
		drawRect(150, 2, 220, 14, 0x90000000);
		mc.fontRendererObj.drawShadedString("Combat", 152, 4, 0xffffffff);

		drawRect(250, 2, 320, 14, 0x90000000);
		mc.fontRendererObj.drawShadedString("Render", 252, 4, 0xffffffff);

		drawRect(350, 2, 420, 14, 0x90000000);
		mc.fontRendererObj.drawShadedString("Movement", 352, 4, 0xffffffff);

		drawRect(450, 2, 520, 14, 0x90000000);
		mc.fontRendererObj.drawShadedString("Player", 452, 4, 0xffffffff);
		
	    for (Module m : InitClient.modules) {
	    	if(m.getModuleName() == "ClickGui")
	    		continue;
	    	
			drawRect(getXLoc(m), getYLoc(m), getXLoc2(m), getYLoc(m) + 14, getSuitedColor(m));
			mc.fontRendererObj.drawShadedString(m.getModuleName(), getXLoc(m) + 2, getYLoc(m) + 2, 0xffffffff);
	    }
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		
		if(mouseButton == 0) {
			for(Module m : InitClient.modules) {
				if(mouseY - getYLoc(m) < 12) {
					if(mouseX - getXLoc(m) < 0)
						continue;
					if(mouseY - getYLoc(m) < 0)
						continue;
					
						if(mouseX - getXLoc(m) < 70) {
							m.toggle();
						}
				}
			}
		}
	}
}