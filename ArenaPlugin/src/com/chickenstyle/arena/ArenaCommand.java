package com.chickenstyle.arena;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.chickenstyle.arena.Configs.BoostersLocation;
import com.chickenstyle.arena.Configs.Config;
import com.chickenstyle.arena.Configs.Deaths;
import com.chickenstyle.arena.Configs.Kills;
import com.chickenstyle.arena.Configs.Rank;

import me.clip.placeholderapi.PlaceholderAPI;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;

public class ArenaCommand implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
			if (args.length == 1) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					if (player.hasPermission("arena.admin")) {
					switch (args[0].toLowerCase()) {
						case "reload":
								Main.getPlugin(Main.class).reloadConfig();
								Kills.configReload();
								Rank.configReload();
								Deaths.configReload();
								BoostersLocation.configReload();
								player.sendMessage(ChatColor.GREEN + "Configs has been reloaded!");
						break;
					
						
						
						case "spawnjoinnpc":
								if (args.length == 1) {
							        NPCRegistry registry = CitizensAPI.getNPCRegistry();
							        String name = ChatColor.translateAlternateColorCodes('&', Config.getString("joinnpcname"));
							        NPC npc = registry.createNPC(EntityType.PLAYER, name);
							        npc.setProtected(true);
							        npc.spawn(player.getLocation());
							        player.sendMessage(ChatColor.GREEN + "NPC has been created!");
								}
						break;
						
						case "spawnshopnpc":
							if (args.length == 1) {
						        NPCRegistry registry = CitizensAPI.getNPCRegistry();
						        String name = ChatColor.translateAlternateColorCodes('&', Config.getString("shopnpcname"));
						        NPC npc = registry.createNPC(EntityType.PLAYER, name);
						        npc.setProtected(true);
						        npc.spawn(player.getLocation());
						        player.sendMessage(ChatColor.GREEN + "NPC has been created!");
							}
					break;
						
						
						case "spawnleavenpc":
							if (args.length == 1) {
						        NPCRegistry registry = CitizensAPI.getNPCRegistry();
						        String name = ChatColor.translateAlternateColorCodes('&', Config.getString("leavenpcname"));
						        NPC npc = registry.createNPC(EntityType.PLAYER, name);
						        npc.setProtected(true);
						        npc.spawn(player.getLocation());
						        player.sendMessage(ChatColor.GREEN + "NPC has been created!");
							}
						break;
						
						case "setlobby":
							if (args.length == 1) {
								if (player.hasPermission("arena.admin")) {
									Config.setLobbyLocation(player.getLocation());
									player.sendMessage(ChatColor.GREEN + "Lobby's location has been set succesfully!");
								}
							}
							break;
							
						
						case "spawnbooster":
							if (player.hasPermission("arena.admin")) {
								player.sendMessage(ChatColor.GREEN + "Booster has been spawned!");
								Location loc = BoostersLocation.getRandomLocation();
								ArmorStand stand = loc.getWorld().spawn(loc, ArmorStand.class);
								stand.setVisible(false);
								stand.setGravity(false);
								stand.setArms(true);
								stand.setCustomName(ChatColor.translateAlternateColorCodes('&', Config.getString("boostertitle")));
								stand.setCustomNameVisible(true);
								int id = Bukkit.getScheduler().scheduleAsyncRepeatingTask(Main.getPlugin(Main.class), new Animate(stand,loc), 0, 1);
								ItemStack helmet = new ItemStack(Material.EMERALD_BLOCK);
								ItemMeta meta = helmet.getItemMeta();
								meta.setDisplayName(id + "");
								helmet.setItemMeta(meta);
								stand.setHelmet(helmet);
							}

							break;
							
							
						case "addlocation":
							if (player.hasPermission("arena.admin")) {
								BoostersLocation.addBooster(player.getLocation().subtract(0,0.5,0));
								player.sendMessage(ChatColor.GREEN + "Location has been added!");
							}
						break;
						
						case "debug":
							player.sendMessage(PlaceholderAPI.setPlaceholders(player, "%arena_maxkills%"));
						break;
						
						default:
						
					}
				  } else {
					  player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getString("nopermissioncommand")));
				  }
				}
			}
		return false;
	}

}
