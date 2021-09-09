package me.danny125.peroxide;

import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.opengl.Display;

import me.danny125.peroxide.Events.Event;
import me.danny125.peroxide.modules.Module;
import me.danny125.peroxide.modules.combat.KillAura;
import me.danny125.peroxide.modules.render.Fullbright;
import me.danny125.peroxide.player.Scaffold;

public class InitClient {
	//array of the modules
	static CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList<Module>();
	
	public static void initialize() {
		Display.setTitle("Peroxide 0.1");
		
		// initialize modules
		modules.add(new Fullbright());
		modules.add(new KillAura());
		modules.add(new Scaffold());
	}
	// keypress
	public static void keyPress(int Key) {
		for(Module m: modules) {
			if(m.getKey() == Key) {
				m.toggle();
			}
		}
	}
	// do something on event
	public static void onEvent(Event e) {
		for(Module m: modules)
		{
			if(m.toggled)
				m.onEvent(e);
		}
	}
}
