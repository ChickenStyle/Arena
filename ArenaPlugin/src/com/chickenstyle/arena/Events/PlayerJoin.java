package com.chickenstyle.arena.Events;

import java.util.ArrayList;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.chickenstyle.arena.ScoreBoard;
import com.chickenstyle.arena.Configs.Config;
import com.earth2me.essentials.api.UserDoesNotExistException;

public class PlayerJoin implements Listener {
	@EventHandler(priority = EventPriority.MONITOR)
	public void onChangeEvent(PlayerJoinEvent e) throws UserDoesNotExistException {
		Player player = e.getPlayer();
		World world = player.getWorld();
		if (world.getName().equals(Config.getString("arenaworld"))) {
			ArrayList<String> list = Config.getList("ScoreBoard");
			new ScoreBoard(player, list);
		}
		
		
	}
}
