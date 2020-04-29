package com.chickenstyle.arena.Events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import com.chickenstyle.arena.Main;
import com.chickenstyle.arena.Utils;
import com.chickenstyle.arena.Boosters.Boosters;
import com.chickenstyle.arena.Configs.Config;
import com.earth2me.essentials.api.UserDoesNotExistException;

public class BoosterClickeEvent implements Listener{
	@EventHandler
	public void onInteract(PlayerInteractAtEntityEvent e) throws UserDoesNotExistException {
		if (e.getRightClicked().getType().equals(EntityType.ARMOR_STAND)) {
			Player player = e.getPlayer();
			ArmorStand stand = (ArmorStand) e.getRightClicked();
			if (stand.getHelmet().getType().equals(Material.EMERALD_BLOCK)) {
				e.setCancelled(true);
				int id = Integer.valueOf(stand.getHelmet().getItemMeta().getDisplayName());
				stand.remove();
				Bukkit.getScheduler().cancelTask(id);
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', Utils.replacePlaceHolders(Config.getString("boostercollectmessage"), player)));
				player.getWorld().spawnParticle(Particle.FLAME, stand.getLocation(), 200,0,0,0,0.2);
				Main.BoosterAmount =  Main.BoosterAmount - 1;
				Boosters.applyBooster(player);
			}
		}
	}
}
