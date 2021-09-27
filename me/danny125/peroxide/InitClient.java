package me.danny125.peroxide;

import java.awt.Color;
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

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import me.danny125.peroxide.Events.Event;
import me.danny125.peroxide.Events.MotionEvent;
import me.danny125.peroxide.alts.AltManager;
import me.danny125.peroxide.alts.GuiAddAlt;
import me.danny125.peroxide.alts.GuiAddAlt.AddAltThread;
import me.danny125.peroxide.modules.Module;
import me.danny125.peroxide.modules.combat.AntiBots;
import me.danny125.peroxide.modules.combat.AutoWtap;
import me.danny125.peroxide.modules.combat.Extinguish;
import me.danny125.peroxide.modules.combat.TargetStrafe;
import me.danny125.peroxide.modules.combat.TempKillAura;
import me.danny125.peroxide.modules.movement.AirJump;
import me.danny125.peroxide.modules.movement.AutoSprint;
import me.danny125.peroxide.modules.movement.Disabler;
import me.danny125.peroxide.modules.movement.Flight;
import me.danny125.peroxide.modules.movement.Speed;
import me.danny125.peroxide.modules.player.NoFall;
import me.danny125.peroxide.modules.player.NoSlow;
import me.danny125.peroxide.modules.player.Regen;
import me.danny125.peroxide.modules.player.Scaffold;
import me.danny125.peroxide.modules.player.AntiKnockback;
import me.danny125.peroxide.modules.player.AutoRespawn;
import me.danny125.peroxide.modules.player.Discord_RPC;
import me.danny125.peroxide.modules.render.ClickGui;
import me.danny125.peroxide.modules.render.ColorModule;
import me.danny125.peroxide.modules.render.ESP;
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
	public static CustomFontRenderer customFontHuge;
	public static String clientdisplay = "Peroxide 0.6.3";
	
	public static String clientname = "Peroxide";
    public static String clientversion = "0.6.3";

	public static String newline = System.getProperty("line.separator");
	
	public static AltManager altManager;
	
	public String[] addedalts;

	public static void initialize() {
		Display.setTitle(clientdisplay);
		
		altManager = new AltManager();

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
		modules.add(new Rotations());
		modules.add(new Disabler());
		modules.add(new AntiKnockback());
		modules.add(new TempKillAura());
		modules.add(new Regen());
		modules.add(new Flight());
		modules.add(new AirJump());
		modules.add(new NoSlow());
		modules.add(new Discord_RPC());
		modules.add(new ColorModule());
		modules.add(new ESP());
		modules.add(new AutoRespawn());
		modules.add(new AutoWtap());
		modules.add(new Extinguish());
		
		Minecraft.getMinecraft().gameSettings.guiScale = 2;
		
		// add FontRenderer to:do add changeable size

		/*
		 * you can change the font, don't change the booleans tho or it will look shit
		 */
		customFont = new CustomFontRenderer(new Font("Arial", Font.PLAIN, 18), true, true);
		customFontBig = new CustomFontRenderer(new Font("Arial", Font.PLAIN, 24), true, true);
		customFontHuge = new CustomFontRenderer(new Font("Arial", Font.PLAIN, 48), true, true);
		
		loadAlts("PeroxideAlts.txt",true);
		
		loadConfig("PeroxideConfig.txt");
		if(isModuleToggled("DiscordRPC")) {
			startRPC();
		}
	}

	public static Color getColor() {
		int red = 0;
		int green = 0;
		int blue = 0;
		for(Module m: modules) {
			if(m.getModuleName() == "Color") {
				if(m.toggled) {
					for(Setting s: m.ListSettings()) {
						if(s instanceof NumberSetting) {
							NumberSetting setting = (NumberSetting) s;
							if(s.name == "Red") {
								red = (int) Math.round(setting.getValue());
							}
							if(s.name == "Green") {
								green = (int) Math.round(setting.getValue());
							}
							if(s.name == "Blue") {
								blue = (int) Math.round(setting.getValue());
								return new Color(red,green,blue);
							}
						}
						
					}
				}else {
					return new Color(191,11,255);
				}
			}
		}
		return new Color(191,11,255);
	}
	

	
	public static void saveConfig(String configfile) {
    	// save the configuration file
    	String config = "";
		for(Module m: InitClient.modules) {
			
				for(Setting s : m.ListSettings()) {
					if(s instanceof NumberSetting) {
						NumberSetting setting = (NumberSetting) s;
						config = config + m.getModuleName() + s.name + setting.getValue() + newline;
					}
					if(s instanceof KeyBindSetting) {
						KeyBindSetting setting = (KeyBindSetting) s;
						config = config + m.getModuleName() + s.name + setting.getCode() + newline;
					}
					if(s instanceof ModeSetting) {
						ModeSetting setting = (ModeSetting) s;
						config = config + m.getModuleName() + s.name + setting.getIndex() + newline;
					}
					if(s instanceof BooleanSetting) {
						BooleanSetting setting = (BooleanSetting) s;
						config = config + m.getModuleName() + s.name + setting.isToggled() + newline;
					}
			}
				config = config + m.getModuleName() + "Toggled" + m.toggled + newline;
				
				File file = new File(configfile);
				if(file.exists()) {
					file.delete();
					try (PrintWriter out = new PrintWriter(configfile)) {
					    out.println(config);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}else {
					try (PrintWriter out = new PrintWriter(configfile)) {
					    out.println(config);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
		}
	}
	public static DiscordRPC lib = DiscordRPC.INSTANCE;
	public static void startRPC() {
        
        String applicationId = "890054171376619581";
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.ready = (user) -> System.out.println("Ready!");
        lib.Discord_Initialize(applicationId, handlers, true, null);
        DiscordRichPresence presence = new DiscordRichPresence();
        presence.startTimestamp = System.currentTimeMillis() / 1000; // epoch second
        presence.details = "pwning some noobs";
        presence.largeImageKey = "newlogo";
        lib.Discord_UpdatePresence(presence);
        // in a worker thread
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                lib.Discord_RunCallbacks();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {}
            }
        }, "RPC-Callback-Handler").start();
	}
	
	public static void stopRPC() {
		lib.Discord_Shutdown();
		lib.Discord_ClearPresence();
	}
	
	public static void loadAlts(String alts, boolean initialCheck) {
		File config = new File(alts);

		if (config.exists()) {

				Path path = Paths.get(alts);
				List<String> lines = null;
				try {
					lines = Files.readAllLines(path, StandardCharsets.UTF_8);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				System.out.println(lines.size());
				for (String line : lines) {
					String username;
					String password;
					String[] userpass = line.split(":");
					

					
					try {
						username = userpass[0];
						password = userpass[1];
						AddAltThread.checkAndAddAlt(username, password,initialCheck);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
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
								double valueDouble = Double.parseDouble(value);
								setting.setValue(valueDouble);
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
