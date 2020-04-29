package com.chickenstyle.arena.Events;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import com.chickenstyle.arena.ScoreBoard;
import com.chickenstyle.arena.Configs.Config;
import com.earth2me.essentials.api.UserDoesNotExistException;

public class ChangeWorldEvent implements Listener {
	@EventHandler
	public void onWorldChange(PlayerChangedWorldEvent e) throws UserDoesNotExistException {
		Player player = e.getPlayer();
		World world = player.getWorld();
		if (world.getName().equals(Config.getString("arenaworld"))) {
			ArrayList<String> list = Config.getList("ScoreBoard");
			new ScoreBoard(player, list);
		}
		
		if (e.getFrom().getName().equals(Config.getString("arenaworld")) && !world.getName().equals("lobby")) {
			player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		}
	}
}
