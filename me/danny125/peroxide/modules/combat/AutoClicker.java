package me.danny125.peroxide.modules.combat;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

import me.danny125.peroxide.Events.Event;
import me.danny125.peroxide.Events.EventUpdate;
import me.danny125.peroxide.modules.Module;
import me.danny125.peroxide.settings.NumberSetting;
import me.danny125.peroxide.utilities.time.Timer;
import net.minecraft.client.Minecraft;

public class AutoClicker extends Module {

	public long lastMS = System.currentTimeMillis();

	public void reset() {
		this.lastMS = System.currentTimeMillis();
	}

	public boolean hasTimeElapsed(long time, boolean reset) {
		if (System.currentTimeMillis() - this.lastMS > time) {
			if (reset) {
				reset();
			}
			return true;
		}

		return false;
	}

	public void setTime(long Time) {
		this.lastMS = Time;
	}

	public long getTime() {
		return System.currentTimeMillis() - this.lastMS;
	}

	// simulate real mouse clicks for added bypassing
	public static void click() throws AWTException {
		Robot bot = new Robot();
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}
	
	public NumberSetting cps = new NumberSetting("CPS",8,1,20,1);
	
	public AutoClicker() {
		super("AutoClicker",0,Category.COMBAT);
		this.addSettings(cps);
	}
	
	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			if(hasTimeElapsed((long) (1000 / cps.getValue()),true)) {
				if(Minecraft.getMinecraft().currentScreen == null) {
					try {
						click();
					} catch (AWTException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
	}

}
