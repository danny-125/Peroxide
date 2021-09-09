package me.danny125.peroxide.utilities;

import net.minecraft.entity.Entity;

public class IsVillager {
//utility for kill aura / smart clicker to made the code cleaner.
	public static boolean isVillager(Entity entity) {
		String name = entity.getDisplayName().getFormattedText();
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
													return false;
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
		return true;
	}
}
