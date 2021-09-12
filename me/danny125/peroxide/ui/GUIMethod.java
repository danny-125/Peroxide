package me.danny125.peroxide.ui;

import java.awt.Color;
import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import me.danny125.peroxide.InitClient;
import me.danny125.peroxide.Events.EventKey;
import me.danny125.peroxide.modules.Module;
import me.danny125.peroxide.modules.Module.Category;
import me.danny125.peroxide.settings.BooleanSetting;
import me.danny125.peroxide.settings.KeyBindSetting;
import me.danny125.peroxide.settings.ModeSetting;
import me.danny125.peroxide.settings.NumberSetting;
import me.danny125.peroxide.settings.Setting;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GUIMethod extends GuiScreen {
	
	//this class is retarded, rewriting it
	
	boolean isClicked;
	int key;
	boolean ChangingKey;
	
	NumberSetting dragging;
	
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
		DrawClickGUI(mouseX, mouseY, mouseButton, ClickType.CLICK);
		super.mouseClicked(mouseX, mouseY, mouseButton);
    }
	
	public void DrawClickGUI(int mouseX, int mouseY, int button, ClickType type) {
		drawDefaultBackground();
		drawRect(150, 2, 220, 14, 0xD2000000);
		mc.fontRendererObj.drawShadedString("Combat", 152, 4, 0xffffffff);

		drawRect(250, 2, 320, 14, 0xD2000000);
		mc.fontRendererObj.drawShadedString("Render", 252, 4, 0xffffffff);

		drawRect(350, 2, 420, 14, 0xD2000000);
		mc.fontRendererObj.drawShadedString("Movement", 352, 4, 0xffffffff);

		drawRect(450, 2, 520, 14, 0xD2000000);
		mc.fontRendererObj.drawShadedString("Player", 452, 4, 0xffffffff);
		
	    	for(Category c : Category.values()) {
	    	
	    		int count = 1;
	    		
	    		for(Module m : InitClient.modules) {
	    			if(m.category == c) {
	    				if(m.getModuleName() == "ClickGui")
	    					continue;
	    			
			drawRect(getXLoc(m),(int)(count * 13.9), getXLoc2(m), (int)(count * 13.9 + 14), getSuitedColor(m));
			mc.fontRendererObj.drawShadedString(m.getModuleName(), getXLoc(m) + 2, (float) (count * 13.9 + 2), 0xffffffff);
	    	
			
			
			if(isHovered(getXLoc(m),(int)(count * 13.9), getXLoc2(m), (int)(count * 13.9 + 14), mouseX, mouseY)) {
				if(Mouse.isButtonDown(0) && isClicked == false) {
					m.toggle();
					isClicked = true;
				}
				if(Mouse.isButtonDown(1) && isClicked == false) {
					m.expanded = !m.expanded;
					isClicked = true;
				}
				
			}
			
			count++;
			
			if(m.expanded) {
			for(Setting setting : m.settings) {
				if(setting instanceof BooleanSetting) {
					
					BooleanSetting bool = (BooleanSetting)setting;
					
					drawRect(getXLoc(m),(int)(count * 13.9), getXLoc2(m), (int)(count * 13.9 + 14), new Color(0,0,0).getRGB());
					mc.fontRendererObj.drawShadedString(setting.name + " " + bool.isToggled(), getXLoc(m) + 2, (float) (count * 13.9 + 2), 0xffffffff);
					
					if(isHovered(getXLoc(m),(int)(count * 13.9), getXLoc2(m), (int)(count * 13.9 + 14), mouseX, mouseY)) {
						
						if(Mouse.isButtonDown(0) && isClicked == false) {
							bool.toggle();
							isClicked = true;
						}
						
					}
					
					count++;
				}
				if(setting instanceof ModeSetting) {
					
					ModeSetting mode = (ModeSetting)setting;
					
					drawRect(getXLoc(m),(int)(count * 13.9), getXLoc2(m), (int)(count * 13.9 + 14), new Color(0,0,0).getRGB());
					mc.fontRendererObj.drawShadedString(setting.name + " " + mode.getMode(), getXLoc(m) + 2, (float) (count * 13.9 + 2), 0xffffffff);
					
					if(isHovered(getXLoc(m),(int)(count * 13.9), getXLoc2(m), (int)(count * 13.9 + 14), mouseX, mouseY)) {
						
						if(Mouse.isButtonDown(0) && isClicked == false) {
							mode.cycle();
							isClicked = true;
						}
						
					}
					
					count++;
				}
				if(setting instanceof NumberSetting) {
					//i hate this
					
					int width = 70;
					
					NumberSetting number = (NumberSetting) setting;
					
					float percent = (float) ((number.getValue() - number.getMin()) / (number.getMax() - number.getMin())),
                            numberWidth = (float) (percent * width);

					drawRect(getXLoc(m),(int)(count * 13.9), getXLoc2(m), (int)(count * 13.9 + 14), new Color(0,0,0).getRGB());
					
					 if (dragging != null && dragging == number) {
						 double mousePercent = Math.min(1, Math.max(0, ((mouseX) - (getXLoc(m))) / width)),
                                 newValue = (mousePercent * (number.getMax() - number.getMin())) + number.getMin();
                         number.setValue(newValue);
                        }
					
                  
                    mc.fontRendererObj.drawString(number.name + " " + number.getValue(), getXLoc(m), (float) (count * 13.9 + 2), -1);
                    
                    
                    //slider
                    Gui.drawRect(getXLoc(m), (int) (count * 13.9 + 10), (int)(getXLoc(m) + numberWidth), (int) (count * 13.9 + 12), new Color(255,0,255).getRGB());;

if(isHovered(getXLoc(m),(int)(count * 13.9), getXLoc2(m), (int)(count * 13.9 + 14), mouseX, mouseY)) {
						
						if(Mouse.isButtonDown(0)) {
							dragging = number;
							isClicked = true;
						}
}
                    
                    
                    count++;
				}
				
				
				if(setting instanceof KeyBindSetting) {
					
					//this should be last
					
					KeyBindSetting keybind = (KeyBindSetting)setting;
					
					drawRect(getXLoc(m),(int)(count * 13.9), getXLoc2(m), (int)(count * 13.9 + 14), new Color(0,0,0).getRGB());
					mc.fontRendererObj.drawShadedString(setting.name + " " + Keyboard.getKeyName(m.keyCode.getCode()), getXLoc(m) + 2, (float) (count * 13.9 + 2), 0xffffffff);
					
					if(isHovered(getXLoc(m),(int)(count * 13.9), getXLoc2(m), (int)(count * 13.9 + 14), mouseX, mouseY)) {
						
						if(Mouse.isButtonDown(0)) {
							ChangingKey = true;
							isClicked = true;
						}
						
						if(ChangingKey) {
							int key = Keyboard.getEventKey();
							
							if(Keyboard.isKeyDown(key)) {
								m.keyCode.code = key;
							}
						}
						
					}
					
					count++;
				}
			}
	    			}
			
	    		}
	    		}
	    		
	    		if (type == ClickType.RELEASE && button == 0) {
		            dragging = null;
		            dragging = null;
		        }
	    	}
		
	}
	
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
			return new Color(0,0,0).getRGB();
		}else {
			return new Color(185,0,225).getRGB();
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
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		DrawClickGUI(mouseX, mouseY, -1, ClickType.DRAW);
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void mouseReleased(int mouseX, int mouseY, int button) {
		DrawClickGUI(mouseX, mouseY, button, ClickType.RELEASE);
		isClicked = false;
		ChangingKey = false;
	}
	@Override
	public boolean doesGuiPauseGame() {
        return false;
    }

	
    public static boolean isHovered(float left, float top, float right, float bottom, int mouseX, int mouseY) {
        return mouseX >= left && mouseY >= top && mouseX < right && mouseY < bottom;
    }
	
    public void keyPress(int key) {
		
    	if(key <= 0)
    		return;
    	
    	key = this.key;
		
	}
    
	public enum ClickType{
		CLICK,
		RELEASE,
		DRAG,
		DRAW
	}
}