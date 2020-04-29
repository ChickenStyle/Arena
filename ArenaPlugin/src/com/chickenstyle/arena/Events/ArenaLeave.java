package com.chickenstyle.arena.Events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.chickenstyle.arena.HiddenStringUtils;
import com.chickenstyle.arena.Configs.Config;

import net.raidstone.wgevents.events.RegionLeftEvent;

public class ArenaLeave implements Listener{
	@EventHandler
	public void ArenaLeaveEvent(RegionLeftEvent e) {
		Player player = e.getPlayer();
		String regionname = e.getRegionName();
		if (regionname.equalsIgnoreCase(Config.getString("regionname"))) {
			for (ItemStack item:player.getInventory().getContents()) {
				if (item != null && item.getType() != null && item.getItemMeta() != null) {
					  if (item.getType().equals(Material.COMPASS)) {
							ItemMeta meta = item.getItemMeta();
							if (meta.getLore() != null && meta.getLore().size() > 0 && HiddenStringUtils.hasHiddenString(meta.getLore().get(0))) {
								 String json = HiddenStringUtils.extractHiddenString(meta.getLore().get(0));
								 Bukkit.getScheduler().cancelTask(Integer.valueOf(json));
								 player.getInventory().clear();
							}	
					  }
				}
			}
		}
	}
}
