package com.chickenstyle.arena.Guis;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.chickenstyle.arena.Configs.Config;

public class BowUpgradeGui {
	public BowUpgradeGui(Player player) {
		
		//Black Glass
		ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
		ItemMeta glassMeta = glass.getItemMeta();
		glassMeta.setDisplayName(" ");
		glass.setItemMeta(glassMeta);
		
		ItemStack flame = new ItemStack(Material.ENCHANTED_BOOK);
		ItemMeta fmeta = flame.getItemMeta();
		fmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f&lFlame &r&e&lI"));
		ArrayList<String> flamelore = new ArrayList<>();
		flamelore.add(ChatColor.translateAlternateColorCodes('&', "&fCost: &e" + Config.getIneger("flameprice") + "©"));
		fmeta.setLore(flamelore);
		flame.setItemMeta(fmeta);
		
		ItemStack knockback = new ItemStack(Material.ENCHANTED_BOOK);
		ItemMeta kmeta = knockback.getItemMeta();
		kmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f&lKnockback &r&e&lI"));
		ArrayList<String> knocklore = new ArrayList<>();
		knocklore.add(ChatColor.translateAlternateColorCodes('&', "&fCost: &e" + Config.getIneger("knockbackprice") + "©"));
		kmeta.setLore(knocklore);
		knockback.setItemMeta(kmeta);
		
		ItemStack power = new ItemStack(Material.ENCHANTED_BOOK);
		ItemMeta pmeta = power.getItemMeta();
		pmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f&lPower &r&e&lI"));
		ArrayList<String> powerlore = new ArrayList<>();
		powerlore.add(ChatColor.translateAlternateColorCodes('&', "&fCost: &e" + Config.getIneger("powerprice") + "©"));
		pmeta.setLore(powerlore);
		power.setItemMeta(pmeta);
		
		ItemStack back = new ItemStack(Material.ARROW);
		ItemMeta bmeta = back.getItemMeta();
		bmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&fGo Back"));
		back.setItemMeta(bmeta);
		
		Inventory gui = Bukkit.createInventory(null, 45 , ChatColor.YELLOW + "" + ChatColor.BOLD + "Bow Upgrades");
		ArrayList<Integer> emptySlot = new ArrayList<Integer>();
		emptySlot.add(20);
		emptySlot.add(22);
		emptySlot.add(24);
		emptySlot.add(40);
		for (int i = 0; i < gui.getSize(); i++) {
			if (!emptySlot.contains(i)) {
				gui.setItem(i, glass);
			}
			if (i == 20) {
				gui.setItem(i, flame);
			}
			if (i == 22) {
				gui.setItem(i, power);
			}
			if (i == 24) {
				gui.setItem(i, knockback);
			}
			if (i == 40) {
				gui.setItem(i, back);
			}

		}
		player.openInventory(gui);
	}
}
