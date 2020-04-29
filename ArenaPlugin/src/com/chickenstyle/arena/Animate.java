package com.chickenstyle.arena;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

public class Animate extends BukkitRunnable {
	ArmorStand stand;
	Location loc;
	boolean up = true;
	
	public Animate(ArmorStand stand,Location loc) {
		this.stand = stand;
		this.loc = loc;
	}

	@Override
	public void run() {
		EulerAngle oldrot = stand.getHeadPose();
		EulerAngle newrot = oldrot.add(0, 0.1f, 0);
		stand.setHeadPose(newrot);
		if (stand.getLocation().getY() - loc.getY() >= 0.5) {
			up = false;
		}
		if (stand.getLocation().getY() - loc.getY() <= 0) {
			up = true;
		}
		
		if (up == true) {
			stand.teleport(stand.getLocation().add(0,0.025,0));;
		}
		if (up == false) {
			stand.teleport(stand.getLocation().subtract(0,0.025,0));
		}
	}
	
}
