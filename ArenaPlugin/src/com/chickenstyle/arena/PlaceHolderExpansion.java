package com.chickenstyle.arena;

import org.bukkit.entity.Player;

import com.chickenstyle.arena.Configs.Config;
import com.chickenstyle.arena.Configs.Deaths;
import com.chickenstyle.arena.Configs.Kills;
import com.chickenstyle.arena.Configs.MaxKills;
import com.chickenstyle.arena.Configs.Rank;
import com.chickenstyle.arena.Events.InventoryClick;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class PlaceHolderExpansion extends PlaceholderExpansion{

	@Override
	public String getAuthor() {
		return "ChickenStyle";
	}

	@Override
	public String getIdentifier() {
		return "arena";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}
	
	@Override
	public boolean canRegister() {
		return true;
	}
	
	@Override
	public boolean persist() {
		return true;
	}
	
	@Override
	public String onPlaceholderRequest(Player player, String params) {
		if (player == null) {
			return "";
		}
		
		if (params.equals("maxkills")) {
			return MaxKills.getKills(player) + "";
		}
		
		if (params.equals("totalkills")) {
			return Kills.getKills(player) + "";
		}
		
		if (params.equals("totaldeaths")) {
			return Deaths.getDeaths(player) + "";
		}
		
		if (params.equals("player")) {
			return player.getName();
		}
		
		if (params.equals("bet")) {
			if (InventoryClick.bet.containsKey(player)) {
				return InventoryClick.bet.get(player) + "";
			} else {
				return Config.getString("betmessage");
			}
		}
		
		if (params.equals("rank")) {
			return Rank.getRank(player);
		}
		return null;
	}
}
