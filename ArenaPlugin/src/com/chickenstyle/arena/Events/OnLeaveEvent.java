package com.chickenstyle.arena.Events;

import java.util.ArrayList;
import java.util.Collections;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Team;

import com.chickenstyle.arena.ScoreBoard;
import com.chickenstyle.arena.Utils;
import com.chickenstyle.arena.Configs.Config;
import com.earth2me.essentials.api.UserDoesNotExistException;

public class OnLeaveEvent implements Listener {
	@SuppressWarnings("unchecked")
	@EventHandler
	public void onLeave(PlayerQuitEvent e) throws UserDoesNotExistException {
		if (e.getPlayer().getWorld().getName().equals(Config.getString("arenaworld"))) {
			for (ItemStack item:e.getPlayer().getInventory().getContents()) {
				if (item == null || item.getItemMeta() == null) return; 
				if (item.getType().equals(Material.COMPASS)) {
					Bukkit.getScheduler().cancelTask(item.getItemMeta().getCustomModelData());
				}
			}
			e.getPlayer().getInventory().clear();
			e.getPlayer().teleport(Config.getLobbyLocation());
			Player player = e.getPlayer();
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
}
