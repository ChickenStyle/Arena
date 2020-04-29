package com.chickenstyle.arena.Events;

import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.chickenstyle.arena.Configs.Config;
import com.chickenstyle.arena.Guis.PlaceBet;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;

public class openBetMenuEvent implements Listener {
	
	@EventHandler
	public void onSignClick(NPCRightClickEvent e) {
		Player player = e.getClicker();
		NPC npc = e.getNPC();
		if (npc.getName().equals(ChatColor.translateAlternateColorCodes('&', Config.getString("joinnpcname")))) {
			new PlaceBet(player);
			player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
			player.getWorld().spawnParticle(Particle.CRIT, npc.getStoredLocation().clone().add(0,1,0), 200,0,0,0,0.2);
		}
	}
}
