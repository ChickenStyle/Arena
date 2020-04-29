package com.chickenstyle.arena.Events;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.chickenstyle.arena.HiddenStringUtils;
import com.chickenstyle.arena.Main;
import com.chickenstyle.arena.TargetPlayerCompass;
import com.chickenstyle.arena.Utils;
import com.chickenstyle.arena.Configs.Config;
import com.chickenstyle.arena.Events.ShopEvents.ShopEvents;
import com.earth2me.essentials.api.UserDoesNotExistException;

import net.raidstone.wgevents.events.RegionEnteredEvent;

public class checkBet implements Listener{
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onEnter(RegionEnteredEvent e) throws UserDoesNotExistException {
		Player player = e.getPlayer();
		String regionname = e.getRegionName();
		if (regionname.equalsIgnoreCase(Config.getString("regionname"))) {	
			if (!InventoryClick.bet.containsKey(player)) {
				player.teleport(Config.getLobbyLocation());
				for (String msg:Config.getList("noentermessage")) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', Utils.replacePlaceHolders(msg, player)));
				}
				player.playSound(player.getLocation(), Sound.BLOCK_SMOKER_SMOKE, 1f, 1f);
			} else {
				
				boolean hasitem = false;
				for (ItemStack item: player.getInventory().getContents()) {
					if (item != null && item.getType() != null && item.getItemMeta() != null) {
					  if (item.getType().equals(Material.COMPASS)) {
						hasitem = true;
					}
				  }
				}
				if (hasitem == false) {
				int id = Bukkit.getScheduler().scheduleAsyncRepeatingTask(Main.getPlugin(Main.class), new TargetPlayerCompass(player), 0, 5);
				//Compass
				ItemStack compass = new ItemStack(Material.COMPASS);
				ItemMeta meta = compass.getItemMeta();
				ArrayList<String> lore = new ArrayList<String>();
				lore.add(HiddenStringUtils.encodeString(id + ""));
				meta.setLore(lore);
				meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Tracker");
				compass.setItemMeta(meta);
				
				//Sword
				ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
				ItemMeta smeta = sword.getItemMeta();
				smeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&lDiamond Sword"));
				
				if (ShopEvents.swordench.get(player) != null && !ShopEvents.swordench.get(player).isEmpty()) {
					for (Enchantment ench:ShopEvents.swordench.get(player)) {
						smeta.addEnchant(ench, 1, true);
					}
				}
				smeta.setUnbreakable(true);
				sword.setItemMeta(smeta);
				ShopEvents.swordench.put(player, new ArrayList<Enchantment>());
				//Bow
				ItemStack bow = new ItemStack(Material.BOW);
				ItemMeta bmeta = bow.getItemMeta();
				bmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e&lInfinity Bow"));
				if (ShopEvents.bowench.get(player) != null && !ShopEvents.bowench.get(player).isEmpty()) {
					for (Enchantment ench:ShopEvents.bowench.get(player)) {
						bmeta.addEnchant(ench, 1, true);
					}
				}
				bmeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
				bmeta.setUnbreakable(true);
				bow.setItemMeta(bmeta);
				ShopEvents.bowench.put(player, new ArrayList<Enchantment>());
				
				player.getInventory().setItem(0, sword);
				player.getInventory().setHelmet(Utils.createItem(Material.DIAMOND_HELMET, "&b&lDiamond Helmet", 1,true));
				player.getInventory().setChestplate(Utils.createItem(Material.DIAMOND_CHESTPLATE, "&b&lDiamond Chestplate", 1,true));
				player.getInventory().setLeggings(Utils.createItem(Material.DIAMOND_LEGGINGS, "&b&lDiamond Leggings", 1,true));
				player.getInventory().setBoots(Utils.createItem(Material.DIAMOND_BOOTS, "&b&lDiamond Boots", 1,true));
				player.getInventory().setItem(1, bow);
				player.getInventory().setItem(9, Utils.createItem(Material.ARROW, "&7&lArrow", 1 ,true));
				player.getInventory().setItem(8, compass);
				
			  }
			}
		}
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if (InventoryClick.bet.get(e.getPlayer()) != null) {
			e.setCancelled(true);
		}
	}
}
