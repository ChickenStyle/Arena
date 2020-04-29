package com.chickenstyle.arena;

import java.util.ArrayList;
import java.util.Collections;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.chickenstyle.arena.Configs.Config;
import com.earth2me.essentials.api.UserDoesNotExistException;


public class ScoreBoard {
    static public String genEntry(int slot) {
        return ChatColor.values()[slot].toString();
    }
    
    static public String getFirstSplit(String s) {
        return s.length()>16 ? s.substring(0, 16) : s;
    }

    static public String getSecondSplit(String s) {
        if(s.length()>32) {
            s = s.substring(0, 32);
        }
        return s.length()>16 ? s.substring(16) : "";
    }
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public ScoreBoard(Player player,ArrayList<String> list) throws UserDoesNotExistException {
		ArrayList<String> clonedlist = (ArrayList<String>) list.clone();
		Collections.reverse(clonedlist);
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = board.registerNewObjective("ScoreBoard", "Dummy");
		obj.setDisplayName(ChatColor.translateAlternateColorCodes('&', Config.getString("scoreboardtitle")));
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		for (int i = 1; i < clonedlist.size() + 1;i++) {
			Team team = board.registerNewTeam("line" + i);
			String line = clonedlist.get(i - 1);
			team.addEntry(genEntry(i));
				String text = ChatColor.translateAlternateColorCodes('&', Utils.replacePlaceHolders(line, player));
				String prefix = getFirstSplit(text);
				String suffix = getFirstSplit(ChatColor.getLastColors(prefix) + getSecondSplit(text));
				team.setPrefix(prefix);
				team.setSuffix(suffix);
				obj.getScore(genEntry(i)).setScore(i);
		}
		player.setScoreboard(board);
	}
}
