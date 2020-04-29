package com.chickenstyle.arena;

import java.util.ArrayList;
import java.util.Collections;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;

import com.chickenstyle.arena.Configs.BoostersLocation;
import com.chickenstyle.arena.Configs.Config;
import com.chickenstyle.arena.Configs.Deaths;
import com.chickenstyle.arena.Configs.Kills;
import com.chickenstyle.arena.Configs.MaxKills;
import com.chickenstyle.arena.Configs.Rank;
import com.chickenstyle.arena.Events.ArenaLeave;
import com.chickenstyle.arena.Events.BoosterClickeEvent;
import com.chickenstyle.arena.Events.ChangeWorldEvent;
import com.chickenstyle.arena.Events.GetBetEvent;
import com.chickenstyle.arena.Events.InventoryClick;
import com.chickenstyle.arena.Events.KillEvent;
import com.chickenstyle.arena.Events.LeaveArenaEvent;
import com.chickenstyle.arena.Events.OnLeaveEvent;
import com.chickenstyle.arena.Events.PlayerJoin;
import com.chickenstyle.arena.Events.checkBet;
import com.chickenstyle.arena.Events.openBetMenuEvent;
import com.chickenstyle.arena.Events.ShopEvents.ShopEvents;
import com.earth2me.essentials.api.UserDoesNotExistException;

public class Main extends JavaPlugin implements Listener{
	
	public static int BoosterAmount = 0;
	
	public void onEnable(){
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Arena Plugin was enabled!");
		Bukkit.getPluginManager().registerEvents(this, this);
		Bukkit.getPluginManager().registerEvents(new openBetMenuEvent(), this);
		Bukkit.getPluginManager().registerEvents(new InventoryClick(), this);
		Bukkit.getPluginManager().registerEvents(new KillEvent(), this);
		Bukkit.getPluginManager().registerEvents(new LeaveArenaEvent(), this);
		Bukkit.getPluginManager().registerEvents(new checkBet(), this);
		Bukkit.getPluginManager().registerEvents(new GetBetEvent(), this);
		Bukkit.getPluginManager().registerEvents(new ChangeWorldEvent(), this);
		Bukkit.getPluginManager().registerEvents(new BoosterClickeEvent(), this);
		Bukkit.getPluginManager().registerEvents(new OnLeaveEvent(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
		Bukkit.getPluginManager().registerEvents(new ArenaLeave(), this);
		Bukkit.getPluginManager().registerEvents(new ShopEvents(), this);
		new PlaceHolderExpansion().register();
		this.getConfig().options().copyDefaults();
	    saveDefaultConfig();
	    getCommand("arena").setExecutor(new ArenaCommand());
	    getCommand("leave").setExecutor(new LeaveCommand());
	    new Kills(this);
	    new Rank(this);
	    new Deaths(this);
	    new BoostersLocation(this);
	    new MaxKills(this);
	    int delay = getConfig().getInt("boosterdelay");
	    new BukkitRunnable() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				int maxamount = getConfig().getInt("maxboostersamount");
				if (maxamount != BoosterAmount) {
					BoosterAmount++;
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
					for (Player player: getServer().getWorld(Config.getString("arenaworld")).getPlayers()) {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getString("boosterspawnmessage")));
					}
				}
			}
		}.runTaskTimer(this, 20*delay, 20*delay);
	}
	
	@SuppressWarnings("unchecked")
	public void onDisable(){
		World world = getServer().getWorld(Config.getString("arenaworld"));
		for (Entity en:world.getEntities()) {
			if (en.getType().equals(EntityType.ARMOR_STAND)) {
				ArmorStand stand = (ArmorStand) en;
				if (stand.getCustomName().equals(ChatColor.translateAlternateColorCodes('&', Config.getString("boostertitle")))) {
					en.remove();
				}
			} else if (en.getType().equals(EntityType.PLAYER)) {
				Player player = (Player) en;
				player.teleport(Config.getLobbyLocation());
				player.getInventory().clear();
				ArrayList<String> list = (ArrayList<String>) Config.getList("ScoreBoard").clone();
				Collections.reverse(list);
				for (int i = 0; i < list.size();i++) {
					if (list.get(i).contains("%totalkills%")) {
						Team team = player.getScoreboard().getTeam("line" + (i + 1));
						String line = list.get(i);
						String text;
						try {
							text = ChatColor.translateAlternateColorCodes('&', Utils.replacePlaceHolders(line, player));
							String prefix = ScoreBoard.getFirstSplit(text);
							String suffix = ScoreBoard.getFirstSplit(ChatColor.getLastColors(prefix) + ScoreBoard.getSecondSplit(text));
							team.setPrefix(prefix);
							team.setSuffix(suffix);
						} catch (UserDoesNotExistException e) {
							e.printStackTrace();
						}
					}
					if (list.get(i).contains("%bet%")) {
						Team team = player.getScoreboard().getTeam("line" + (i + 1));
						String line = list.get(i);
						String text;
						try {
							text = ChatColor.translateAlternateColorCodes('&', Utils.replacePlaceHolders(line, player));
							String prefix = ScoreBoard.getFirstSplit(text);
							String suffix = ScoreBoard.getFirstSplit(ChatColor.getLastColors(prefix) + ScoreBoard.getSecondSplit(text));
							team.setPrefix(prefix);
							team.setSuffix(suffix);
						} catch (UserDoesNotExistException e) {
							e.printStackTrace();
						}
					}
					if (list.get(i).contains("%bet%")) {
						Team team = player.getScoreboard().getTeam("line" + (i + 1));
						String line = list.get(i);
						String text;
						try {
							text = ChatColor.translateAlternateColorCodes('&', Utils.replacePlaceHolders(line, player));
							String prefix = ScoreBoard.getFirstSplit(text);
							String suffix = ScoreBoard.getFirstSplit(ChatColor.getLastColors(prefix) + ScoreBoard.getSecondSplit(text));
							team.setPrefix(prefix);
							team.setSuffix(suffix);
						} catch (UserDoesNotExistException e) {
							e.printStackTrace();
						}
					}
					if (list.get(i).contains("%rank%")) {
						Team team = player.getScoreboard().getTeam("line" + (i + 1));
						String line = list.get(i);
						String text;
						try {
							text = ChatColor.translateAlternateColorCodes('&', Utils.replacePlaceHolders(line, player));
							String prefix = ScoreBoard.getFirstSplit(text);
							String suffix = ScoreBoard.getFirstSplit(ChatColor.getLastColors(prefix) + ScoreBoard.getSecondSplit(text));
							team.setPrefix(prefix);
							team.setSuffix(suffix);
						} catch (UserDoesNotExistException e) {
							e.printStackTrace();
						}
					}
					if (list.get(i).contains("%totaldeaths%")) {
						Team team = player.getScoreboard().getTeam("line" + (i + 1));
						String line = list.get(i);
						String text;
						try {
							text = ChatColor.translateAlternateColorCodes('&', Utils.replacePlaceHolders(line, player));
							String prefix = ScoreBoard.getFirstSplit(text);
							String suffix = ScoreBoard.getFirstSplit(ChatColor.getLastColors(prefix) + ScoreBoard.getSecondSplit(text));
							team.setPrefix(prefix);
							team.setSuffix(suffix);
						} catch (UserDoesNotExistException e) {
							e.printStackTrace();
						}
					}
					
					if (list.get(i).contains("%maxkills%")) {
						Team team = player.getScoreboard().getTeam("line" + (i + 1));
						String line = list.get(i);
						String text;
						try {
							text = ChatColor.translateAlternateColorCodes('&', Utils.replacePlaceHolders(line, player));
							String prefix = ScoreBoard.getFirstSplit(text);
							String suffix = ScoreBoard.getFirstSplit(ChatColor.getLastColors(prefix) + ScoreBoard.getSecondSplit(text));
							team.setPrefix(prefix);
							team.setSuffix(suffix);
						} catch (UserDoesNotExistException e) {
							e.printStackTrace();
						}
					}
			}
		  }
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public static void onJoin(PlayerJoinEvent e) {
	    ShopEvents.bowench.put(e.getPlayer(), new ArrayList<Enchantment>());
	    ShopEvents.swordench.put(e.getPlayer(), new ArrayList<Enchantment>());
		if (!Kills.containsPlayer(e.getPlayer())) {
			Kills.setKills(e.getPlayer(), 0);
		}
		if (!Rank.containsPlayer(e.getPlayer())) {
			Rank.setRank(e.getPlayer(), "&fRookie");
		}
		if (!Deaths.containsPlayer(e.getPlayer())) {
			Deaths.setDeaths(e.getPlayer(),0);
		}
		if (!MaxKills.containsPlayer(e.getPlayer())) {
			MaxKills.setKills(e.getPlayer(), 0);
		}
	}
}
