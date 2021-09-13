package me.danny125.peroxide.modules.combat;



import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import me.danny125.peroxide.Events.Event;
import me.danny125.peroxide.Events.EventUpdate;
import me.danny125.peroxide.modules.Module;
import me.danny125.peroxide.utilities.IsTeammate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;

public class TempKillAura extends Module {

	public TempKillAura() {
		super("TempKillAura",0,Category.COMBAT);
	}
	public static void click() throws AWTException{
	    Robot bot = new Robot();   
	    bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
	    bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

	}
	public void onEvent(Event e) {
			if(e instanceof EventUpdate) {
				if(e.isPre()) {
					List<EntityLivingBase> targets = (List<EntityLivingBase>) mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
					targets = targets.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < 4 && entity != mc.thePlayer).collect(Collectors.toList());
					targets.sort(Comparator.comparingDouble(entity -> ((EntityLivingBase)entity).getDistanceToEntity(mc.thePlayer)));
					
						if(!targets.isEmpty()) {
							EntityLivingBase entity = targets.get(0);
										
							if(!entity.isInvisible()) {
								if(!(entity.getDisplayName().getFormattedText() == "")) {
									
									if(!IsTeammate.isOnSameTeam(entity)) {
									
										String name = entity.getDisplayName().getFormattedText();
										
										System.out.println(name);
										
										if(!name.contains("Farmer")) {
											if(!name.contains("Shepherd")) {
												if(!name.contains("Armorer")) {
													if(!name.contains("Weapon Smith")) {
														if(!name.contains("Librarian")) {
															if(!name.contains("Tool Smith")) {
																if(!name.contains("Fisherman")) {
																	if(!name.contains("Leatherworker")){
																		if(!name.contains("Fletcher")) {
																			if(!name.contains("Butcher")) {
																				if(!name.contains("Cleric")) {

																					mc.thePlayer.swingItem();
																					mc.getNetHandler().addToSendQueue(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
																					mc.playerController.attackEntity(mc.thePlayer, entity);

																				}
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
				}
			}
	}
}
