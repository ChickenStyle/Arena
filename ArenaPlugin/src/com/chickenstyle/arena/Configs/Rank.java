package com.chickenstyle.arena.Configs;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.chickenstyle.arena.Main;



public class Rank {
	
	private static File file;
	private static YamlConfiguration config;
    public Rank(Main main) {
  	  file = new File(main.getDataFolder(), "Ranks.yml");
  	 if (!file.exists()) {
  		 try {
				 file.createNewFile();
		    	 config = YamlConfiguration.loadConfiguration(file);
		    	  	try {
		    				config.save(file);
		    		    	config = YamlConfiguration.loadConfiguration(file);
		    			} catch (IOException e) {
		    				e.printStackTrace();
		    			}
			} catch (IOException e) {
				e.printStackTrace();
			}
  		 
  	 }
  	config = YamlConfiguration.loadConfiguration(file);
   }
    
    
    static public void setRank(Player player,String rank) {
    	config.set("Ranks." + player.getUniqueId(), rank);
    	  try {
    		config.save(file);
    	    config = YamlConfiguration.loadConfiguration(file);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    static public String getRank(Player player) {
		return config.getString("Ranks." + player.getUniqueId());
    }
    
    static public boolean containsPlayer(Player player) {
    	if (config.get("Ranks." + player.getUniqueId()) != null) {
    		return true;
    	}
    	return false;
    }
	
	static public void configReload() {
   	 config = YamlConfiguration.loadConfiguration(file);
		try {
			config.save(file);
			config = YamlConfiguration.loadConfiguration(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
