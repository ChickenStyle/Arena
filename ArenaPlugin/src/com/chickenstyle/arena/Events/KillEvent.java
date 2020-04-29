package com.chickenstyle.arena.Events;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.Team;

import com.chickenstyle.arena.HiddenStringUtils;
import com.chickenstyle.arena.ScoreBoard;
import com.chickenstyle.arena.Utils;
import com.chickenstyle.arena.Configs.Config;
import com.chickenstyle.arena.Configs.Deaths;
import com.chickenstyle.arena.Configs.Kills;
import com.chickenstyle.arena.Configs.MaxKills;
import com.chickenstyle.arena.Configs.Rank;
import com.earth2me.essentials.api.UserDoesNotExistException;

public class KillEvent implements Listener {
	public static HashMap<Player,Integer> killstreak = new HashMap<>();
	public static HashMap<Player,Long> combat = new HashMap<>();
	@SuppressWarnings("unchecked")
	@EventHandler
	public void onkill(EntityDamageByEntityEvent e) throws UserDoesNotExistException {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			Player enemy = (Player) e.getEntity();
			Player attacker = (Player) e.getDamager();
			if (enemy.getHealth() - e.getFinalDamage() <= 0) {
				if (enemy.getWorld().getName().equals(Config.getString("arenaworld"))) {
					e.setCancelled(true);
					//Killed message
					enemy.setHealth(20.0);
					combat.remove(enemy);
					enemy.teleport(Config.getLobbyLocation());
					InventoryClick.bet.remove(enemy);
					Deaths.addDeath(enemy);
					if (MaxKills.getKills(enemy) < Kills.getKills(enemy)) {
						MaxKills.setKills(enemy, killstreak.get(enemy));
					}
					killstreak.put(enemy, 0);
					for (ItemStack item:enemy.getInventory().getContents()) {
						if (item != null && item.getType() != null && item.getItemMeta() != null) {
							  if (item.getType().equals(Material.COMPASS)) {
									ItemMeta meta = item.getItemMeta();
									if (meta.getLore() != null && meta.getLore().size() > 0 && HiddenStringUtils.hasHiddenString(meta.getLore().get(0))) {
										 String json = HiddenStringUtils.extractHiddenString(meta.getLore().get(0));
										 Bukkit.getScheduler().cancelTask(Integer.valueOf(json));
									}	
							}
						  }
					}
					enemy.getInventory().clear();
					String deathtitle = Utils.replacePlaceHolders(ChatColor.translateAlternateColorCodes('&', Config.getString("deathtitle")), enemy);
					String deathsubtitle = Utils.replacePlaceHolders(ChatColor.translateAlternateColorCodes('&', Config.getString("deathsubtitle")), enemy);
					Utils.sendTitleAndSubTitle(enemy, deathtitle, deathsubtitle);
					enemy.playSound(enemy.getLocation(), Sound.BLOCK_ANVIL_BREAK, 1f, 1f);
					
					//Killer Data
					Kills.addKill(attacker);
					//Kill Streak Counter
					
					if (killstreak.containsKey(attacker)) {
						killstreak.put(attacker, killstreak.get(attacker) + 1);
					} else {
						killstreak.put(attacker, 1);
					}
					
					if (attacker.getHealth() <= 16.00) {
						attacker.setHealth(attacker.getHealth() + 4);
					} else {
						attacker.setHealth(attacker.getHealth() + (20 - attacker.getHealth()));
					}
					int bet = InventoryClick.bet.get(attacker);
					
					if (killstreak.get(attacker) >= 5) {
						bet = (int) (bet * 1.3);
					}
					if (killstreak.get(attacker) >= 3) {
						bet = (int) (bet * 1.2);
					}
					if (killstreak.get(attacker) >= 1) {
						bet = (int) (bet * 1.1);
					}
					InventoryClick.bet.put(attacker, bet);
					ArrayList<String> messages = Config.getList("higherbetmessage");
					for (String msg:messages) {
						attacker.sendMessage(ChatColor.translateAlternateColorCodes('&', Utils.replacePlaceHolders(msg, attacker)));
					}
					String killstreaktitle = ChatColor.translateAlternateColorCodes('&', Utils.replacePlaceHolders(Config.getString("killstreaktitle"), attacker));
					String killstreaksubtitle = ChatColor.translateAlternateColorCodes('&', Utils.replacePlaceHolders(Config.getString("killstreaksubtitle"), attacker));
					if (killstreak.get(attacker) == 3) {
						Utils.sendTitleAndSubTitle(attacker, killstreaktitle, killstreaksubtitle);
					}
					if (killstreak.get(attacker) == 5) {
						Utils.sendTitleAndSubTitle(attacker, killstreaktitle, killstreaksubtitle);
					}
					if (killstreak.get(attacker) == 10) {
						Utils.sendTitleAndSubTitle(attacker, killstreaktitle, killstreaksubtitle);
					}
					attacker.playSound(attacker.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);

					
					//Rank check
					if (Kills.getKills(attacker) == 25) {
						Rank.setRank(attacker, "&7Intermediate");
						String message = Utils.replacePlaceHolders(Config.getString("rankupmessage"), attacker);
						attacker.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
					}
					if (Kills.getKills(attacker) == 50) {
						Rank.setRank(attacker, "&eSemi-Pro");
						String message = Utils.replacePlaceHolders(Config.getString("rankupmessage"), attacker);
						attacker.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
					}
					if (Kills.getKills(attacker) == 100) {
						Rank.setRank(attacker, "&a&lPro");
						String message = Utils.replacePlaceHolders(Config.getString("rankupmessage"), attacker);
						attacker.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
					}
					if (Kills.getKills(attacker) == 150) {
						Rank.setRank(attacker, "&c&lKnight");
						String message = Utils.replacePlaceHolders(Config.getString("rankupmessage"), attacker);
						attacker.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
					}
					if (Kills.getKills(attacker) == 250) {
						Rank.setRank(attacker, "&6&lAssassin");
						String message = Utils.replacePlaceHolders(Config.getString("rankupmessage"), attacker);
						attacker.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
					}
					if (Kills.getKills(attacker) == 500) {
						Rank.setRank(attacker, "&6&lAssassin&r&e+");
						String message = Utils.replacePlaceHolders(Config.getString("rankupmessage"), attacker);
						attacker.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
					}
					if (Kills.getKills(attacker) == 1000) {
						Rank.setRank(attacker, "&6&lAssassin&r&e++");
						String message = Utils.replacePlaceHolders(Config.getString("rankupmessage"), attacker);
						attacker.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
					}
					ArrayList<String> list = (ArrayList<String>) Config.getList("ScoreBoard").clone();
					Collections.reverse(list);
					for (int i = 0; i < list.size();i++) {
						if (list.get(i).contains("%totalkills%")) {
							Team team = attacker.getScoreboard().getTeam("line" + (i + 1));
							String line = list.get(i);
							String text = ChatColor.translateAlternateColorCodes('&', Utils.replacePlaceHolders(line, attacker));
							String prefix = ScoreBoard.getFirstSplit(text);
							String suffix = ScoreBoard.getFirstSplit(ChatColor.getLastColors(prefix) + ScoreBoard.getSecondSplit(text));
							team.setPrefix(prefix);
							team.setSuffix(suffix);
						}
						if (list.get(i).contains("%bet%")) {
							Team team = attacker.getScoreboard().getTeam("line" + (i + 1));
							String line = list.get(i);
							String text = ChatColor.translateAlternateColorCodes('&', Utils.replacePlaceHolders(line, attacker));
							String prefix = ScoreBoard.getFirstSplit(text);
							String suffix = ScoreBoard.getFirstSplit(ChatColor.getLastColors(prefix) + ScoreBoard.getSecondSplit(text));
							team.setPrefix(prefix);
							team.setSuffix(suffix);
						}
						if (list.get(i).contains("%bet%")) {
							Team team = enemy.getScoreboard().getTeam("line" + (i + 1));
							String line = list.get(i);
							String text = ChatColor.translateAlternateColorCodes('&', Utils.replacePlaceHolders(line, enemy));
							String prefix = ScoreBoard.getFirstSplit(text);
							String suffix = ScoreBoard.getFirstSplit(ChatColor.getLastColors(prefix) + ScoreBoard.getSecondSplit(text));
							team.setPrefix(prefix);
							team.setSuffix(suffix);
						}
						if (list.get(i).contains("%rank%")) {
							Team team = attacker.getScoreboard().getTeam("line" + (i + 1));
							String line = list.get(i);
							String text = ChatColor.translateAlternateColorCodes('&', Utils.replacePlaceHolders(line, attacker));
							String prefix = ScoreBoard.getFirstSplit(text);
							String suffix = ScoreBoard.getFirstSplit(ChatColor.getLastColors(prefix) + ScoreBoard.getSecondSplit(text));
							team.setPrefix(prefix);
							team.setSuffix(suffix);
						}
						if (list.get(i).contains("%totaldeaths%")) {
							Team team = enemy.getScoreboard().getTeam("line" + (i + 1));
							String line = list.get(i);
							String text = ChatColor.translateAlternateColorCodes('&', Utils.replacePlaceHolders(line, enemy));
							String prefix = ScoreBoard.getFirstSplit(text);
							String suffix = ScoreBoard.getFirstSplit(ChatColor.getLastColors(prefix) + ScoreBoard.getSecondSplit(text));
							team.setPrefix(prefix);
							team.setSuffix(suffix);
						}
						
						if (list.get(i).contains("%maxkills%")) {
							Team team = enemy.getScoreboard().getTeam("line" + (i + 1));
							String line = list.get(i);
							String text = ChatColor.translateAlternateColorCodes('&', Utils.replacePlaceHolders(line, enemy));
							String prefix = ScoreBoard.getFirstSplit(text);
							String suffix = ScoreBoard.getFirstSplit(ChatColor.getLastColors(prefix) + ScoreBoard.getSecondSplit(text));
							team.setPrefix(prefix);
							team.setSuffix(suffix);
						}
						if (list.get(i).contains("%killstreak%")) {
							Team team = enemy.getScoreboard().getTeam("line" + (i + 1));
							String line = list.get(i);
							String text = ChatColor.translateAlternateColorCodes('&', Utils.replacePlaceHolders(line, attacker));
							String prefix = ScoreBoard.getFirstSplit(text);
							String suffix = ScoreBoard.getFirstSplit(ChatColor.getLastColors(prefix) + ScoreBoard.getSecondSplit(text));
							team.setPrefix(prefix);
							team.setSuffix(suffix);
						}
					}

				}
			} else {
				combat.put(enemy, System.currentTimeMillis());
				combat.put(attacker,System.currentTimeMillis());
			}
			
		}

	}
    
}
