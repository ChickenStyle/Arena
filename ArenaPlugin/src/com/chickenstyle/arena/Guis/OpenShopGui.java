package com.chickenstyle.arena.Guis;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class OpenShopGui {
	public OpenShopGui(Player player) {
		
		//Black Glass
		ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
		ItemMeta glassMeta = glass.getItemMeta();
		glassMeta.setDisplayName(" ");
		glass.setItemMeta(glassMeta);
		
		ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta smeta = sword.getItemMeta();
		smeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&lSword Upgrades"));
		smeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		sword.setItemMeta(smeta);
		
		ItemStack bow = new ItemStack(Material.BOW);
		ItemMeta bmeta = bow.getItemMeta();
		bmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e&lBow Upgrades"));
		bmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		bow.setItemMeta(bmeta);
		
		
		Inventory gui = Bukkit.createInventory(null, 45 , ChatColor.translateAlternateColorCodes('&', "&6&lShop"));
		ArrayList<Integer> emptySlot = new ArrayList<Integer>();
		emptySlot.add(21);
		emptySlot.add(23);
		for (int i = 0; i < gui.getSize(); i++) {
			if (!emptySlot.contains(i)) {
				gui.setItem(i, glass);
			}
			if (i == 21) {
				gui.setItem(21, bow);
			}
			if (i == 23) {
				gui.setItem(23, sword);
			}
		}
		player.openInventory(gui);
	}
}
