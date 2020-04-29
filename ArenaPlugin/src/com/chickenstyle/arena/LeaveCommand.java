package com.chickenstyle.arena;

import java.util.ArrayList;
import java.util.Collections;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import com.chickenstyle.arena.Configs.Config;
import com.chickenstyle.arena.Events.InventoryClick;
import com.chickenstyle.arena.Events.KillEvent;
import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;

public class LeaveCommand implements CommandExecutor {

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (InventoryClick.bet.containsKey(player)) {
				if (KillEvent.combat.containsKey(player)) {
					long secondsleft = ((KillEvent.combat.get(player) / 1000) + 5) - (System.currentTimeMillis() / 1000);
					if (secondsleft > 0) {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getString("combatmessage")));
						return true;
					}
	 			}
				
				try {
					Economy.setMoney(player.getName(), Economy.getMoney(player.getName()) + InventoryClick.bet.get(player));
				} catch (NoLoanPermittedException | UserDoesNotExistException e) {
					e.printStackTrace();
				}
				player.teleport(Config.getLobbyLocation());
				try {
					String title = Utils.replacePlaceHolders(ChatColor.translateAlternateColorCodes('&', Config.getString("getbettitle")), player);
					String subtitle = Utils.replacePlaceHolders(ChatColor.translateAlternateColorCodes('&', Config.getString("getbetsubtitle")), player);
					Utils.sendTitleAndSubTitle(player, title, subtitle);
				} catch (UserDoesNotExistException e) {

					e.printStackTrace();
				}
				player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
				InventoryClick.bet.remove(player);
				ArrayList<String> list = (ArrayList<String>) Config.getList("ScoreBoard").clone();
				Collections.reverse(list);
				for (int i = 0; i < list.size();i++) {
					if (list.get(i).contains("%bet%")) {
						Team team = player.getScoreboard().getTeam("line" + (i + 1));
						String line = list.get(i);
						try {
							String text = ChatColor.translateAlternateColorCodes('&', Utils.replacePlaceHolders(line, player));
							String prefix = ScoreBoard.getFirstSplit(text);
							String suffix = ScoreBoard.getFirstSplit(ChatColor.getLastColors(prefix) + ScoreBoard.getSecondSplit(text));
							team.setPrefix(prefix);
							team.setSuffix(suffix);
						} catch (UserDoesNotExistException e) {
							e.printStackTrace();
						}
					}
					if (list.get(i).contains("%balance%")) {
						Team team = player.getScoreboard().getTeam("line" + (i + 1));
						String line = list.get(i);
						
						try {
							String text = ChatColor.translateAlternateColorCodes('&', Utils.replacePlaceHolders(line, player));
							String prefix = ScoreBoard.getFirstSplit(text);
							String suffix = ScoreBoard.getFirstSplit(ChatColor.getLastColors(prefix) + ScoreBoard.getSecondSplit(text));
							team.setPrefix(prefix);
							team.setSuffix(suffix);
						} catch (UserDoesNotExistException e) {
							e.printStackTrace();
						}
					}
				}
			} else {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getString("leavenobet")));
			}
		}
		return false;
	}

}
