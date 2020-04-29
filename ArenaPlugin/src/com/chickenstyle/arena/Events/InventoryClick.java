package com.chickenstyle.arena.Events;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Team;

import com.chickenstyle.arena.ScoreBoard;
import com.chickenstyle.arena.Utils;
import com.chickenstyle.arena.Configs.Config;
import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;

public class InventoryClick implements Listener {
	public static HashMap<Player,Integer> bet = new HashMap<>();
	
	public boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    return true;
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	@EventHandler
	public void onClick(InventoryClickEvent e) throws UserDoesNotExistException, NoLoanPermittedException {
		Player player = (Player) e.getWhoClicked();
		if (e.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', "&6&lPlace Your Stake"))) {
			e.setCancelled(true);
			if (e.getClickedInventory() == null) return;
			if (e.getClickedInventory().getType().toString() == "CHEST") {
				int slot = e.getSlot();
				ItemStack item = e.getClickedInventory().getItem(slot);
				if (item == null) return;
				if (item.getItemMeta() == null) return;
				if (item.getType() == Material.BLACK_STAINED_GLASS_PANE) return;
				if (isInteger(e.getClickedInventory().getItem(slot).getItemMeta().getDisplayName().split(" ")[1])) {
					long money = (long) Economy.getMoney(player.getName());
					int betprice = Integer.valueOf(e.getClickedInventory().getItem(slot).getItemMeta().getDisplayName().split(" ")[1]);
					if ((long) money >= betprice) {
						if (bet.get(player) != null ) {
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getString("secondbetmessage")));
							player.closeInventory();
							player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 1f, 1f);
							return;
						}
						bet.put(player, betprice);
						Economy.setMoney(player.getName(), money - betprice);
							String titlemessage = Utils.replacePlaceHolders(Config.getString("bettitle"), player);
							String subtitlemessage = Utils.replacePlaceHolders(Config.getString("betsubtitle"), player);
							KillEvent.killstreak.put(player, 0);
							Utils.sendTitleAndSubTitle(player, titlemessage, subtitlemessage);
							player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
							player.getInventory().clear();
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
							
							
					} else {
						String message = Config.getString("cantafford");
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
					}
				}
				player.closeInventory();
			}
			
		}
	}
	
}
