package com.chickenstyle.arena.Guis;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlaceBet {
	public PlaceBet(Player player) {
		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.WHITE + "Click here to place the bet!");
		
		//Black Glass
		ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
		ItemMeta glassMeta = glass.getItemMeta();
		glassMeta.setDisplayName(" ");
		glass.setItemMeta(glassMeta);
		
		//Very Small Bet
		ItemStack verysmall = new ItemStack(Material.WOODEN_SWORD);
		ItemMeta vsmeta = verysmall.getItemMeta();
		vsmeta.setDisplayName(ChatColor.YELLOW + "Bet 100 ©");
		vsmeta.setLore(lore);
		verysmall.setItemMeta(vsmeta);

		//Small Bet
		ItemStack small = new ItemStack(Material.STONE_SWORD);
		ItemMeta smeta = small.getItemMeta();
		smeta.setDisplayName(ChatColor.YELLOW + "Bet 1000 ©");
		smeta.setLore(lore);
		small.setItemMeta(smeta);
		//Medium Bet
		ItemStack medium = new ItemStack(Material.IRON_SWORD);
		ItemMeta mmeta = medium.getItemMeta();
		mmeta.setDisplayName(ChatColor.YELLOW + "Bet 5000 ©");
		mmeta.setLore(lore);
		medium.setItemMeta(mmeta);
		//Large Bet
		ItemStack large = new ItemStack(Material.GOLDEN_SWORD);
		ItemMeta lmeta = large.getItemMeta();
		lmeta.setDisplayName(ChatColor.YELLOW + "Bet 10000 ©");
		lmeta.setLore(lore);
		large.setItemMeta(lmeta);
		//Giant Bet
		ItemStack giant = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta gmeta = giant.getItemMeta();
		gmeta.setDisplayName(ChatColor.YELLOW + "Bet 50000 ©");
		gmeta.setLore(lore);
		giant.setItemMeta(gmeta);
		
		
		Inventory gui = Bukkit.createInventory(null, 45 , ChatColor.translateAlternateColorCodes('&', "&6&lPlace Your Stake"));
		ArrayList<Integer> emptySlot = new ArrayList<Integer>();
		emptySlot.add(20);
		emptySlot.add(21);
		emptySlot.add(22);
		emptySlot.add(23);
		emptySlot.add(24);
		for (int i = 0; i < gui.getSize(); i++) {
			if (!emptySlot.contains(i)) {
				gui.setItem(i, glass);
			}
			if (i == 20) {
				gui.setItem(20, verysmall);
			}
			if (i == 21) {
				gui.setItem(21, small);
			}
			if (i == 22) {
				gui.setItem(22, medium);
			}
			if (i == 23) {
				gui.setItem(23, large);
			}
			if (i == 24) {
				gui.setItem(24, giant);
			}
		}
		player.openInventory(gui);
	}
	
}
