package com.chickenstyle.arena;

import java.lang.reflect.Constructor;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.chickenstyle.arena.Configs.Config;
import com.chickenstyle.arena.Configs.Deaths;
import com.chickenstyle.arena.Configs.Kills;
import com.chickenstyle.arena.Configs.MaxKills;
import com.chickenstyle.arena.Configs.Rank;
import com.chickenstyle.arena.Events.InventoryClick;
import com.chickenstyle.arena.Events.KillEvent;
import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.UserDoesNotExistException;

public class Utils {
	@SuppressWarnings("deprecation")
	public static String replacePlaceHolders(String message,Player player) throws UserDoesNotExistException {
		int balance = (int) Economy.getMoney(player.getName());
		if (!InventoryClick.bet.containsKey(player)) {
			return message.replace("%bet%", Config.getString("betmessage"))
						  .replace("%player%", player.getName())
					      .replace("%balance%", balance + "©")
					      .replace("%rank%",Rank.getRank(player))
					      .replace("%totalkills%", Kills.getKills(player) + "")
					      .replace("%totaldeaths%", Deaths.getDeaths(player) + "")
					      .replace("%maxkills%", MaxKills.getKills(player) + "")
					      .replace("%killstreak%",KillEvent.killstreak.get(player) + "");
		}
		int betprice = InventoryClick.bet.get(player);
		if (!KillEvent.killstreak.containsKey(player)) {
			return message.replace("%bet%", betprice + " ©")
			          .replace("%player%", player.getName())
			          .replace("%balance%", balance + " ©")
				      .replace("%rank%",Rank.getRank(player))
				      .replace("%totalkills%", Kills.getKills(player) + "")
				      .replace("%totaldeaths%", Deaths.getDeaths(player) + "")
				      .replace("%maxkills%", MaxKills.getKills(player) + "")
				      .replace("%killstreak%",0 + "");
		}
		return message.replace("%bet%", betprice + " ©")
			          .replace("%player%", player.getName())
			          .replace("%balance%", balance + " ©")
				      .replace("%rank%",Rank.getRank(player))
				      .replace("%totalkills%", Kills.getKills(player) + "")
				      .replace("%totaldeaths%", Deaths.getDeaths(player) + "")
				      .replace("%maxkills%", MaxKills.getKills(player) + "")
				      .replace("%killstreak%",KillEvent.killstreak.get(player) + "");
	}
	
	public static void sendTitleAndSubTitle(Player player, String title, String subtitle) {
		try {
			Object enumTitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null);
			Object subTitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null);
			Object chat = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + title.replace("&", "§") + "\"}");
			Object subline = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + subtitle.replace('&', '§') + "\"}");
		
			Constructor<?> titleConstuctor = getNMSClass("PacketPlayOutTitle").getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), int.class, int.class, int.class);
			Object packet = titleConstuctor.newInstance(enumTitle,chat, 10, 20, 10);
			Object subpacket = titleConstuctor.newInstance(subTitle,subline, 10, 20, 10);
			sendPacket(player,packet);
			sendPacket(player,subpacket);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public static void sendPacket(Player player, Object packet) {
		try {
			Object handle = player.getClass().getMethod("getHandle").invoke(player);
			Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
			playerConnection.getClass().getMethod("sendPacket",getNMSClass("Packet")).invoke(playerConnection, packet);
		
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	public static Class<?> getNMSClass(String name) {
		String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		try {
			return Class.forName("net.minecraft.server." + version + "." + name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static ItemStack createItem(Material mat,String name,int amount,Boolean unbreakable) {
		ItemStack item = new ItemStack(mat,amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		meta.setUnbreakable(unbreakable);
		item.setItemMeta(meta);
		return item;
	}
	public static ItemStack createItem(Material mat,String name,int amount,Enchantment ench, int level) {
		ItemStack item = new ItemStack(mat,amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		meta.addEnchant(ench, level, true);
		item.setItemMeta(meta);
		return item;
	}
}
