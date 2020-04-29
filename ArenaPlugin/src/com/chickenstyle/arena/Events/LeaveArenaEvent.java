package com.chickenstyle.arena.Events;

import java.util.ArrayList;
import java.util.Collections;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Team;

import com.chickenstyle.arena.ScoreBoard;
import com.chickenstyle.arena.Utils;
import com.chickenstyle.arena.Configs.Config;
import com.chickenstyle.arena.Configs.Deaths;
import com.chickenstyle.arena.Guis.GetBet;
import com.earth2me.essentials.api.UserDoesNotExistException;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;

public class LeaveArenaEvent implements Listener{
	@EventHandler
	public void onSignClick(NPCRightClickEvent e) {
		Player player = e.getClicker();
		NPC npc = e.getNPC();
		if (npc.getName().equals(ChatColor.translateAlternateColorCodes('&', Config.getString("leavenpcname")))) {
			new GetBet(player);
			player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
			player.getWorld().spawnParticle(Particle.CRIT, npc.getStoredLocation().clone().add(0,1,0), 200,0,0,0,0.2);
		}
	}
	
	@SuppressWarnings("unchecked")
	@EventHandler
	public void EntityDamage(EntityDamageEvent e) throws UserDoesNotExistException {
		if (e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			if (!e.getCause().equals(DamageCause.FALL)) {
				if (player.getHealth() - e.getFinalDamage() <= 0) {
					if (player.getWorld().getName().equals(Config.getString("arenaworld"))) {
						e.setCancelled(true);
						player.setHealth(20.0);
						player.teleport(Config.getLobbyLocation());
						InventoryClick.bet.remove(player);
						Deaths.addDeath(player);
						for (ItemStack item:player.getInventory().getContents()) {
							if (item != null && item.getType() != null)  {
								if (item.getType().equals(Material.COMPASS)) {
									Bukkit.getScheduler().cancelTask(item.getItemMeta().getCustomModelData());
								}
					      }
						}
						player.getInventory().clear();
						player.setFireTicks(0);
						String deathtitle = Utils.replacePlaceHolders(ChatColor.translateAlternateColorCodes('&', Config.getString("deathtitle")), player);
						String deathsubtitle = Utils.replacePlaceHolders(ChatColor.translateAlternateColorCodes('&', Config.getString("deathsubtitle")), player);
						Utils.sendTitleAndSubTitle(player, deathtitle, deathsubtitle);
						player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
						ArrayList<String> list = (ArrayList<String>) Config.getList("ScoreBoard").clone();
						Collections.reverse(list);
						for (int i = 0; i < list.size();i++) {
							if (list.get(i).contains("%totalkills%")) {
								Team team = player.getScoreboard().getTeam("line" + (i + 1));
								String line = list.get(i);
								String text = ChatColor.translateAlternateColorCodes('&', Utils.replacePlaceHolders(line, player));
								String prefix = ScoreBoard.getFirstSplit(text);
								String suffix = ScoreBoard.getFirstSplit(ChatColor.getLastColors(prefix) + ScoreBoard.getSecondSplit(text));
								team.setPrefix(prefix);
								team.setSuffix(suffix);
							}
							if (list.get(i).contains("%bet%")) {
								Team team = player.getScoreboard().getTeam("line" + (i + 1));
								String line = list.get(i);
								String text = ChatColor.translateAlternateColorCodes('&', Utils.replacePlaceHolders(line, player));
								String prefix = ScoreBoard.getFirstSplit(text);
								String suffix = ScoreBoard.getFirstSplit(ChatColor.getLastColors(prefix) + ScoreBoard.getSecondSplit(text));
								team.setPrefix(prefix);
								team.setSuffix(suffix);
							}
							if (list.get(i).contains("%bet%")) {
								Team team = player.getScoreboard().getTeam("line" + (i + 1));
								String line = list.get(i);
								String text = ChatColor.translateAlternateColorCodes('&', Utils.replacePlaceHolders(line, player));
								String prefix = ScoreBoard.getFirstSplit(text);
								String suffix = ScoreBoard.getFirstSplit(ChatColor.getLastColors(prefix) + ScoreBoard.getSecondSplit(text));
								team.setPrefix(prefix);
								team.setSuffix(suffix);
							}
							if (list.get(i).contains("%rank%")) {
								Team team = player.getScoreboard().getTeam("line" + (i + 1));
								String line = list.get(i);
								String text = ChatColor.translateAlternateColorCodes('&', Utils.replacePlaceHolders(line, player));
								String prefix = ScoreBoard.getFirstSplit(text);
								String suffix = ScoreBoard.getFirstSplit(ChatColor.getLastColors(prefix) + ScoreBoard.getSecondSplit(text));
								team.setPrefix(prefix);
								team.setSuffix(suffix);
							}
							if (list.get(i).contains("%totaldeaths%")) {
								Team team = player.getScoreboard().getTeam("line" + (i + 1));
								String line = list.get(i);
								String text = ChatColor.translateAlternateColorCodes('&', Utils.replacePlaceHolders(line, player));
								String prefix = ScoreBoard.getFirstSplit(text);
								String suffix = ScoreBoard.getFirstSplit(ChatColor.getLastColors(prefix) + ScoreBoard.getSecondSplit(text));
								team.setPrefix(prefix);
								team.setSuffix(suffix);
							}
							
							if (list.get(i).contains("%maxkills%")) {
								Team team = player.getScoreboard().getTeam("line" + (i + 1));
								String line = list.get(i);
								String text = ChatColor.translateAlternateColorCodes('&', Utils.replacePlaceHolders(line, player));
								String prefix = ScoreBoard.getFirstSplit(text);
								String suffix = ScoreBoard.getFirstSplit(ChatColor.getLastColors(prefix) + ScoreBoard.getSecondSplit(text));
								team.setPrefix(prefix);
								team.setSuffix(suffix);
							}
							if (list.get(i).contains("%killstreak%")) {
								Team team = player.getScoreboard().getTeam("line" + (i + 1));
								String line = list.get(i);
								String text = ChatColor.translateAlternateColorCodes('&', Utils.replacePlaceHolders(line, player));
								String prefix = ScoreBoard.getFirstSplit(text);
								String suffix = ScoreBoard.getFirstSplit(ChatColor.getLastColors(prefix) + ScoreBoard.getSecondSplit(text));
								team.setPrefix(prefix);
								team.setSuffix(suffix);
							}
						}
					}
			     }
			} else {
				e.setCancelled(true);
			}
		}
	}
}
