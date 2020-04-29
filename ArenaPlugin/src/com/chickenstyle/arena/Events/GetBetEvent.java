package com.chickenstyle.arena.Events;

import java.util.ArrayList;
import java.util.Collections;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.scoreboard.Team;

import com.chickenstyle.arena.ScoreBoard;
import com.chickenstyle.arena.Utils;
import com.chickenstyle.arena.Configs.Config;
import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;

public class GetBetEvent implements Listener {
	@SuppressWarnings({ "deprecation", "unchecked" })
	@EventHandler
	public void onClick(InventoryClickEvent e) throws UserDoesNotExistException, NoLoanPermittedException {
		if (e.getClickedInventory() == null) return;
		Player player = (Player) e.getWhoClicked();
		if (e.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', "&a&lCollect Your Winnings"))) {
			e.setCancelled(true);
			int slot = e.getSlot();
			if (e.getClickedInventory().getType().toString() == "CHEST") {
				if (slot == 22) {
					Economy.setMoney(player.getName(), Economy.getMoney(player.getName()) + InventoryClick.bet.get(player));
					player.teleport(Config.getLobbyLocation());
					String title = Utils.replacePlaceHolders(ChatColor.translateAlternateColorCodes('&', Config.getString("getbettitle")), player);
					String subtitle = Utils.replacePlaceHolders(ChatColor.translateAlternateColorCodes('&', Config.getString("getbetsubtitle")), player);
					Utils.sendTitleAndSubTitle(player, title, subtitle);
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
					InventoryClick.bet.remove(player);
					ArrayList<String> list = (ArrayList<String>) Config.getList("ScoreBoard").clone();
					Collections.reverse(list);
					for (int i = 0; i < list.size();i++) {
						if (list.get(i).contains("%bet%")) {
							Team team = player.getScoreboard().getTeam("line" + (i + 1));
							String line = list.get(i);
							String text = ChatColor.translateAlternateColorCodes('&', Utils.replacePlaceHolders(line, player));
							String prefix = ScoreBoard.getFirstSplit(text);
							String suffix = ScoreBoard.getFirstSplit(ChatColor.getLastColors(prefix) + ScoreBoard.getSecondSplit(text));
							team.setPrefix(prefix);
							team.setSuffix(suffix);
						}
						if (list.get(i).contains("%balance%")) {
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
	}
}
