package com.chickenstyle.arena.Events.ShopEvents;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.chickenstyle.arena.Configs.Config;
import com.chickenstyle.arena.Guis.BowUpgradeGui;
import com.chickenstyle.arena.Guis.OpenShopGui;
import com.chickenstyle.arena.Guis.SwordUpgradeGui;
import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;

public class ShopEvents implements Listener{
	public static HashMap<Player,ArrayList<Enchantment>> swordench = new HashMap<>();
	public static HashMap<Player,ArrayList<Enchantment>> bowench = new HashMap<>();
	
	
	@EventHandler
	public void onEntract(NPCRightClickEvent e) {
			Player player = e.getClicker();
			NPC npc = e.getNPC();
			if (npc.getName().equals(ChatColor.translateAlternateColorCodes('&',Config.getString("shopnpcname")))) {
				new OpenShopGui(player);
			    if (!bowench.containsKey(player) || !swordench.containsKey(player)) {
			    	bowench.put(player, new ArrayList<Enchantment>());
			    	swordench.put(player, new ArrayList<Enchantment>());
			    }
			}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick(InventoryClickEvent e) throws UserDoesNotExistException, NoLoanPermittedException {
		Player player = (Player) e.getWhoClicked();
		if (e.getClickedInventory() == null) return;
		if (e.getClickedInventory().getType().toString() == "CHEST") {
		if (e.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', "&6&lShop"))) {
			e.setCancelled(true);
			int slot = e.getSlot();
			if (slot == 21) {
				new BowUpgradeGui(player);
			}
			if (slot == 23) {
				new SwordUpgradeGui(player);
		    }
		}
		if  (e.getView().getTitle().equals(ChatColor.YELLOW + "" + ChatColor.BOLD + "Bow Upgrades")) {
			e.setCancelled(true);
			int slot = e.getSlot();
			if (slot == 20) {
				int money = (int) Economy.getMoney(player.getName());
				int price = Config.getIneger("flameprice");
				if (money > price) {
					ArrayList<Enchantment> list = bowench.get(player);
					if (list.contains(Enchantment.ARROW_FIRE)) {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getString("secondenchantmessage")));
						player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_BREAK, 1f, 1f);
						return;
					}
					list.add(Enchantment.ARROW_FIRE);
					bowench.put(player, list);
					Economy.setMoney(player.getName(), money - price);
					player.closeInventory();
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8|&6&lBlack&e&lTorch&r&8| Upgrade purchased successfully!"));
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8|&6&lBlack&e&lTorch&r&8| &fNew Balance: &e" + Economy.getMoney(player.getName())));
					
					player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8|&6&lBlack&e&lTorch&r&8| &fYou do not have enough money to make this enchantment!"));
				}
			}
			if (slot == 22) {
				int money = (int) Economy.getMoney(player.getName());
				int price = Config.getIneger("powerprice");
				if (money > price) {
					ArrayList<Enchantment> list = bowench.get(player);
					if (list.contains(Enchantment.ARROW_DAMAGE)) {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8|&6&lBlack&e&lTorch&r&8| &fYou do not have enough money to make this enchantment!"));
						player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_BREAK, 1f, 1f);
						return;
					}
					list.add(Enchantment.ARROW_DAMAGE);
					bowench.put(player, list);
					Economy.setMoney(player.getName(), money - price);
					player.closeInventory();
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8|&6&lBlack&e&lTorch&r&8| Upgrade purchased successfully!"));
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8|&6&lBlack&e&lTorch&r&8| &fNew Balance: &e" + Economy.getMoney(player.getName())));
					player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8|&6&lBlack&e&lTorch&r&8| &fYou do not have enough money to make this enchantment!"));
				}
		    }
			if (slot == 24) {
				int money = (int) Economy.getMoney(player.getName());
				int price = Config.getIneger("knockbackprice");
				if (money > price) {
					ArrayList<Enchantment> list = bowench.get(player);
					if (list.contains(Enchantment.ARROW_KNOCKBACK)) {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getString("secondenchantmessage")));
						player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_BREAK, 1f, 1f);
						return;
					}
					list.add(Enchantment.ARROW_KNOCKBACK);
					bowench.put(player, list);
					Economy.setMoney(player.getName(), money - price);
					player.closeInventory();
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8|&6&lBlack&e&lTorch&r&8| Upgrade purchased successfully!"));
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8|&6&lBlack&e&lTorch&r&8| &fNew Balance: &e" + Economy.getMoney(player.getName())));
					player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8|&6&lBlack&e&lTorch&r&8| &fYou do not have enough money to make this enchantment!"));
				}
			}
			if (slot == 40) {
				new OpenShopGui(player);
			}
		}
		if  (e.getView().getTitle().equals(ChatColor.AQUA + "" + ChatColor.BOLD + "Sword Upgrades")) {
			e.setCancelled(true);
			int slot = e.getSlot();
			if (slot == 20) {
				int money = (int) Economy.getMoney(player.getName());
				int price = Config.getIneger("fireaspcetprice");
				if (money > price) {
					ArrayList<Enchantment> list = swordench.get(player);
					if (list.contains(Enchantment.FIRE_ASPECT)) {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getString("secondenchantmessage")));
						player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_BREAK, 1f, 1f);
						return;
					}
					list.add(Enchantment.FIRE_ASPECT);
					swordench.put(player, list);
					Economy.setMoney(player.getName(), money - price);
					player.closeInventory();
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8|&6&lBlack&e&lTorch&r&8| Upgrade purchased successfully!"));
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8|&6&lBlack&e&lTorch&r&8| &fNew Balance: &e" + Economy.getMoney(player.getName())));
					player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8|&6&lBlack&e&lTorch&r&8| &fYou do not have enough money to make this enchantment!"));
				}
			}
			if (slot == 22) {
				int money = (int) Economy.getMoney(player.getName());
				int price = Config.getIneger("sharpnessprice");
				if (money > price) {
					ArrayList<Enchantment> list = swordench.get(player);
					if (list.contains(Enchantment.DAMAGE_ALL)) {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getString("secondenchantmessage")));
						player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_BREAK, 1f, 1f);
						return;
					}
					list.add(Enchantment.DAMAGE_ALL);
					swordench.put(player, list);
					Economy.setMoney(player.getName(), money - price);
					player.closeInventory();
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8|&6&lBlack&e&lTorch&r&8| Upgrades purchased successfully!"));
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8|&6&lBlack&e&lTorch&r&8| &fNew Balance: &e" + Economy.getMoney(player.getName())));
					player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8|&6&lBlack&e&lTorch&r&8| &fYou do not have enough money to make this enchantment!"));
				}
		    }
			if (slot == 24) {
				int money = (int) Economy.getMoney(player.getName());
				int price = Config.getIneger("knockbackprice");
				if (money > price) {
					ArrayList<Enchantment> list = swordench.get(player);
					if (list.contains(Enchantment.KNOCKBACK)) {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getString("secondenchantmessage")));
						player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_BREAK, 1f, 1f);
						return;
					}
					list.add(Enchantment.KNOCKBACK);
					swordench.put(player, list);
					Economy.setMoney(player.getName(), money - price);
					player.closeInventory();
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8|&6&lBlack&e&lTorch&r&8| Upgrade purchased successfully!"));
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8|&6&lBlack&e&lTorch&r&8| &fNew Balance: &e" + Economy.getMoney(player.getName())));
					player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8|&6&lBlack&e&lTorch&r&8| &fYou do not have enough money to make this enchantment!"));
				}
			}
			if (slot == 40) {
				new OpenShopGui(player);
			}
		}
	  }
	}
	
	
}
