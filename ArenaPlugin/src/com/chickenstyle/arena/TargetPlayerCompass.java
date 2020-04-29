package com.chickenstyle.arena;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TargetPlayerCompass extends BukkitRunnable{

	Player player;
	public TargetPlayerCompass(Player player) {
		this.player = player;
	}
	
	@Override
	public void run() {
		if (Compass.getNearest(player) != null) {
			player.setCompassTarget(Compass.getNearest(player).getLocation());
		}
		
	}

}
