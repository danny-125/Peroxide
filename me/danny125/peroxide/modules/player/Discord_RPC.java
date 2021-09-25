package me.danny125.peroxide.modules.player;

import me.danny125.peroxide.InitClient;
import me.danny125.peroxide.modules.Module;

public class Discord_RPC extends Module {

	public Discord_RPC() {
		super("DiscordRPC",0,Category.MISC);
		this.toggled = true;
	}
	
	public void onEnable() {
		InitClient.startRPC();
	}
	public void onDisable() {
		InitClient.stopRPC();
	}

}
