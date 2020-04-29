package com.chickenstyle.arena.Guis;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GetBet {
	public GetBet (Player player) {
		ItemStack goldenblock = new ItemStack(Material.GOLD_INGOT);
		ItemMeta gbmeta = goldenblock.getItemMeta();
		gbmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&lClick here to collect your winnings"));
		goldenblock.setItemMeta(gbmeta);
		
		//Black Glass
		ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
		ItemMeta glassMeta = glass.getItemMeta();
		glassMeta.setDisplayName(" ");
		glass.setItemMeta(glassMeta);
		
		Inventory gui = Bukkit.createInventory(null, 45 , ChatColor.translateAlternateColorCodes('&', "&a&lCollect Your Winnings"));
		ArrayList<Integer> emptySlot = new ArrayList<Integer>();
		emptySlot.add(22);
		for (int i = 0; i < gui.getSize(); i++) {
			if (!emptySlot.contains(i)) {
				gui.setItem(i, glass);
			}
			if (i == 22) {
				gui.setItem(22, goldenblock);
			}
		}
		player.openInventory(gui);
	}
}
