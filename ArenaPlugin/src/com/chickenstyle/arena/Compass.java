package com.chickenstyle.arena;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Compass {
	public static Player getNearest(Player player) {
		double closest = Double.MAX_VALUE;
		Player closestp = null;
		for(Player i : Bukkit.getOnlinePlayers()){
			if (!i.equals(player)) {
				double dist = i.getLocation().distance(player.getLocation());
				if (closest == Double.MAX_VALUE || dist < closest){
					closest = dist;
					closestp = i;
		  	}
			}

		}
		if (closestp == null){
		  return null;
		}
		else{
		  return closestp;
		}
	}

}
