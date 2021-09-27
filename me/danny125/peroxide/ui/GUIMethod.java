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
		drawRect(150, 2, 220, 14, 0xA7000000);
		InitClient.INSTANCE.customFont.drawShadedString("Combat", 152, 4, new Color(255,255,255));

		drawRect(250, 2, 320, 14, 0xA7000000);
		InitClient.INSTANCE.customFont.drawShadedString("Render", 252, 4, new Color(255,255,255));

		drawRect(350, 2, 420, 14, 0xA7000000);
		InitClient.INSTANCE.customFont.drawShadedString("Movement", 352, 4, new Color(255,255,255));

		drawRect(450, 2, 520, 14, 0xA7000000);
		InitClient.INSTANCE.customFont.drawShadedString("Player", 452, 4, new Color(255,255,255));
		
	    	for(Category c : Category.values()) {
	    	
	    		int count = 1;
	    		
	    		for(Module m : InitClient.modules) {
	    			if(m.category == c) {
	    				if(m.getModuleName() == "ClickGui")
	    					continue;
	    			
			drawRect(getXLoc(m),(int)(count * 13.9), getXLoc2(m), (int)(count * 13.9 + 14), getSuitedColor(m));
			InitClient.INSTANCE.customFont.drawShadedString(m.getModuleName(), getXLoc(m) + 2, (float) (count * 13.9 + 2), new Color(255,255,255));
	    	
			
			
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
					
					drawRect(getXLoc(m),(int)(count * 13.9), getXLoc2(m), (int)(count * 13.9 + 14), 0xA7000000);
					InitClient.INSTANCE.customFont.drawShadedString(setting.name + " " + bool.isToggled(), getXLoc(m) + 2, (float) (count * 13.9 + 2), new Color(255,255,255));
					
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
					
					drawRect(getXLoc(m),(int)(count * 13.9), getXLoc2(m), (int)(count * 13.9 + 14), 0xA7000000);
					InitClient.INSTANCE.customFont.drawShadedString(setting.name + " " + mode.getMode(), getXLoc(m) + 2, (float) (count * 13.9 + 2), new Color(255,255,255));
					
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
					
					double percent = ((number.getValue() - number.getMin()) / (number.getMax() - number.getMin())),
                            numberWidth = (percent * width);

					drawRect(getXLoc(m),(int)(count * 13.9), getXLoc2(m), (int)(count * 13.9 + 14), 0xA7000000);
					
					 if (dragging != null && dragging == number) {
						 double largeMousePercent = (mouseX - getXLoc(m)) / 70f;
						 double mousePercent = (double)Math.round(largeMousePercent * 100d) / 100d;
						 double newValue = (mousePercent * (number.getMax() - number.getMin())) + number.getMin();
						 if(newValue < 1.0d) {
							 newValue = 1.0d;
						 }
						 if(newValue > number.getMax()) {
							 newValue = number.getMax();
						 }
						 newValue = Math.round(newValue*100.0d) / 100.0d;
						 System.out.println(newValue);
						 number.setValue(newValue);
                        }
					
                  
					InitClient.INSTANCE.customFont.drawShadedString(number.name + " " + number.getValue(), getXLoc(m), (float) (count * 13.9 + 2), new Color(255,255,255));
                    
                    
                    //slider
                    Gui.drawRect(getXLoc(m), (int) (count * 13.9 + 10), (int)(getXLoc(m) + numberWidth), (int) (count * 13.9 + 12), InitClient.getColor().getRGB());;

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
					
					String name = null;
					String changingmodule = null;
					if(isHovered(getXLoc(m),(int)(count * 13.9), getXLoc2(m), (int)(count * 13.9 + 14), mouseX, mouseY)) {
						
						if(Mouse.isButtonDown(0)) {
							changingmodule = m.getModuleName();
							ChangingKey = true;
							isClicked = true;
						}
						
						if(ChangingKey) {
							int key = Keyboard.getEventKey();
							
							if(Keyboard.isKeyDown(key)) {
								if(key == 14) {
									m.keyCode.code = 0;
								}else {
									m.keyCode.code = key;
								}
							}
						}
						
					}
					if(ChangingKey) {
						if(changingmodule == m.getModuleName()) {
							name = "Changing...";
						}else {
							name = setting.name + " " + Keyboard.getKeyName(m.keyCode.getCode());
						}
					}else {
						name = setting.name + " " + Keyboard.getKeyName(m.keyCode.getCode());
					}
					
					drawRect(getXLoc(m),(int)(count * 13.9), getXLoc2(m), (int)(count * 13.9 + 14), 0xA7000000);
					InitClient.INSTANCE.customFont.drawShadedString(name, getXLoc(m) + 2, (float) (count * 13.9 + 2), new Color(255,255,255));
					

					
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
			return InitClient.getColor().getRGB();
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