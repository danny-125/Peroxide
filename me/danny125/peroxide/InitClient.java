package me.danny125.peroxide;

import java.awt.Font;
import java.net.Proxy;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.opengl.Display;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import me.danny125.peroxide.Events.Event;
import me.danny125.peroxide.Events.MotionEvent;
import me.danny125.peroxide.modules.Module;
import me.danny125.peroxide.modules.combat.AntiBots;
import me.danny125.peroxide.modules.combat.KillAura;
import me.danny125.peroxide.modules.combat.TargetStrafe;
import me.danny125.peroxide.modules.movement.AutoSprint;
import me.danny125.peroxide.modules.movement.Disabler;
import me.danny125.peroxide.modules.movement.LongJump;
import me.danny125.peroxide.modules.movement.Speed;
import me.danny125.peroxide.modules.player.NoFall;
import me.danny125.peroxide.modules.player.Scaffold;
import me.danny125.peroxide.modules.player.Velocity;
import me.danny125.peroxide.modules.render.ClickGui;
import me.danny125.peroxide.modules.render.Fullbright;
import me.danny125.peroxide.modules.render.Rotations;
import me.danny125.peroxide.ui.HUD;
import me.danny125.peroxide.utilities.font.CustomFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

public class InitClient {
	// array of the modules
	public static CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList<Module>();
	public static HUD hud = new HUD();
	
	private Minecraft mc = Minecraft.getMinecraft();

	// Create a INSTANCE so you can reference shit in this class
	public static InitClient INSTANCE = new InitClient();

	public static CustomFontRenderer customFont;
	public static CustomFontRenderer customFontBig;

	public static void initialize() {
		Display.setTitle("Peroxide 0.1");

		// initialize modules
		modules.add(new ClickGui());
		modules.add(new Fullbright());
		modules.add(new Scaffold());
		modules.add(new Speed());
		modules.add(new AutoSprint());
		modules.add(new HUD());
		modules.add(new TargetStrafe());
		modules.add(new NoFall());
		modules.add(new KillAura());
		modules.add(new AntiBots());
		modules.add(new LongJump());
		modules.add(new Rotations());
		modules.add(new Disabler());
		modules.add(new Velocity());
		
		// add FontRenderer to:do add changeable size

		/*
		 * you can change the font, don't change the booleans tho or it will look shit
		 */
		customFont = new CustomFontRenderer(new Font("Arial", Font.PLAIN, 18), true, true);
		customFontBig = new CustomFontRenderer(new Font("Arial", Font.PLAIN, 24), true, true);
		String username = "mr.carungay@gmail.com";
		String password = "Mystery27";
		Session auth = createSession(username, password);
		Minecraft.getMinecraft().session = auth;

	}
	
	  private static Session createSession(String username, String password) {
		    YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
		    YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
		    auth.setUsername(username);
		    auth.setPassword(password);
		    try {
		      auth.logIn();
		      return new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
		    } catch (AuthenticationException localAuthenticationException) {
		      localAuthenticationException.printStackTrace();
		      return null;
		    } 
		  }

	// keypress
	public static void keyPress(int Key) {
		for (Module m : modules) {
			if (m.getKey() == Key) {
				m.toggle();
			}
		}
	}

	public static Module getModule(String name) {
		for (Module m : modules) {
			if (m.name.equalsIgnoreCase(name)) {
				return m;
			}
		}
		return null;
	}

	// toggle modules remotely
	public static void toggleModule(String Module) {
		for (Module m : modules) {
			if (m.getModuleName() == Module) {
				m.toggle();
			}
		}
	}

	// do something on event
	public static void onEvent(Event e) {
		for (Module m : modules) {
			if (m.toggled)
				m.onEvent(e);
		}
	}
}
