package me.danny125.peroxide.utilities;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class IsTeammate {
	//Check to see if player is a teammate, useful for all servers and a few modules
	public static boolean isOnSameTeam(Entity entity) {
        boolean team = false;

        if(entity instanceof EntityPlayer) {
            String n = entity.getDisplayName().getFormattedText();
            if(n.startsWith('\u00a7' + "f") && !n.equalsIgnoreCase(entity.getName()))
                team = (n.substring(0, 6).equalsIgnoreCase(Minecraft.getMinecraft().thePlayer.getDisplayName().getFormattedText().substring(0, 6)));
            else team = (n.substring(0,2).equalsIgnoreCase(Minecraft.getMinecraft().thePlayer.getDisplayName().getFormattedText().substring(0,2)));
        }

        return team;
    }
}
