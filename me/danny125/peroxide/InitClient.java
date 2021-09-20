package me.danny125.peroxide;

import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Proxy;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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
import me.danny125.peroxide.modules.combat.TargetStrafe;
import me.danny125.peroxide.modules.combat.TempKillAura;
import me.danny125.peroxide.modules.movement.AutoSprint;
import me.danny125.peroxide.modules.movement.Disabler;
import me.danny125.peroxide.modules.movement.LongJump;
import me.danny125.peroxide.modules.movement.Speed;
import me.danny125.peroxide.modules.player.NoFall;
import me.danny125.peroxide.modules.player.Regen;
import me.danny125.peroxide.modules.player.Scaffold;
import me.danny125.peroxide.modules.player.AntiKnockback;
import me.danny125.peroxide.modules.render.ClickGui;
import me.danny125.peroxide.modules.render.Fullbright;
import me.danny125.peroxide.modules.render.Rotations;
import me.danny125.peroxide.settings.BooleanSetting;
import me.danny125.peroxide.settings.KeyBindSetting;
import me.danny125.peroxide.settings.ModeSetting;
import me.danny125.peroxide.settings.NumberSetting;
import me.danny125.peroxide.settings.Setting;
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
	public static String clientdisplay = "Peroxide 0.4";

	public static String newline = System.getProperty("line.separator");

	public static void initialize() {
		Display.setTitle(clientdisplay);

		// initialize modules
		modules.add(new ClickGui());
		modules.add(new Fullbright());
		modules.add(new Scaffold());
		modules.add(new Speed());
		modules.add(new AutoSprint());
		modules.add(new HUD());
		modules.add(new TargetStrafe());
		modules.add(new NoFall());
		modules.add(new AntiBots());
		modules.add(new LongJump());
		modules.add(new Rotations());
		modules.add(new Disabler());
		modules.add(new AntiKnockback());
		modules.add(new TempKillAura());
		modules.add(new Regen());

		// add FontRenderer to:do add changeable size

		/*
		 * you can change the font, don't change the booleans tho or it will look shit
		 */
		customFont = new CustomFontRenderer(new Font("Helvetica", Font.PLAIN, 18), true, true);
		customFontBig = new CustomFontRenderer(new Font("Helvetica", Font.PLAIN, 24), true, true);

		loadConfig("PeroxideConfig.txt");

		String username = "wesamso96@hotmail.co.uk";
		String password = "Wbr-1996";
		Session auth = createSession(username, password);
		Minecraft.getMinecraft().session = auth;
	}

	private static Session createSession(String username, String password) {
		YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
		YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) service
				.createUserAuthentication(Agent.MINECRAFT);
		auth.setUsername(username);
		auth.setPassword(password);
		try {
			auth.logIn();
			return new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(),
					auth.getAuthenticatedToken(), "mojang");
		} catch (AuthenticationException localAuthenticationException) {
			localAuthenticationException.printStackTrace();
			return null;
		}
	}

	// load up the config
	public static void loadConfig(String configfile) {
		File config = new File(configfile);

		if (config.exists()) {
			for (Module m : InitClient.modules) {

				Path path = Paths.get(configfile);
				List<String> lines = null;
				try {
					lines = Files.readAllLines(path, StandardCharsets.UTF_8);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				for (String line : lines) {
					if(line.contains(m.getModuleName() + "Toggledtrue") && m.getModuleName() != "ClickGui") {
						m.toggled = true;
					}
					if(line.contains(m.getModuleName() + "Toggledfalse") && m.getModuleName() != "ClickGui") {
						m.toggled = false;
					}
					
					for (Setting s : m.ListSettings()) {
						if (s instanceof NumberSetting) {
							NumberSetting setting = (NumberSetting) s;
							String SettingNoValue = m.getModuleName() + s.name;

							if (line.startsWith(SettingNoValue)) {
								String value = line.substring(SettingNoValue.length());
								String value2 = value.substring(0, value.length() - 2);
								int valueInt = Integer.parseInt(value2);
								setting.setValue(valueInt);
								continue;
							}
						}
						
						if (s instanceof ModeSetting) {
							ModeSetting setting = (ModeSetting) s;
							String SettingNoValue = m.getModuleName() + s.name;

							if (line.startsWith(SettingNoValue)) {
								String value = line.substring(SettingNoValue.length());
								int valueInt = Integer.parseInt(value);
								setting.setIndex(valueInt);
								continue;
							}
						}
						
						if (s instanceof KeyBindSetting) {
							KeyBindSetting setting = (KeyBindSetting) s;
							String SettingNoValue = m.getModuleName() + s.name;

							if (line.startsWith(SettingNoValue)) {
								String value = line.substring(SettingNoValue.length());
								int valueInt = Integer.parseInt(value);
								setting.setCode(valueInt);
								continue;
							}
						}
						
						if (s instanceof BooleanSetting) {
							BooleanSetting setting = (BooleanSetting) s;
							String SettingNoValue = m.getModuleName() + s.name;

							if (line.startsWith(SettingNoValue)) {
								String truefalse = line.substring(SettingNoValue.length());
								if(truefalse.contains("true")) {
									setting.setToggled(true);
									continue;
								}else {
									setting.setToggled(false);
									continue;
								}
							}
						}
					}
					
				}
			}
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

	public static boolean isModuleToggled(String Module) {
		for (Module m : modules) {
			if (m.getModuleName() == Module) {
				if (m.toggled) {
					return true;
				}
			}
		}
		return false;
	}
	
	// do something on event
	public static void onEvent(Event e) {
		for (Module m : modules) {
			if (m.toggled)
				m.onEvent(e);
		}
	}
}
