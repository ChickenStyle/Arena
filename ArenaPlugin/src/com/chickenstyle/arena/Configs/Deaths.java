package com.chickenstyle.arena.Configs;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.chickenstyle.arena.Main;



public class Deaths {
	
	private static File file;
	private static YamlConfiguration config;
    public Deaths(Main main) {
  	  file = new File(main.getDataFolder(), "Deaths.yml");
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
    static public void addDeath(Player player) {
		config.set("Deaths." + player.getUniqueId(), config.getInt("Deaths." + player.getUniqueId().toString()) + 1);
	  	try {
			config.save(file);
	    	config = YamlConfiguration.loadConfiguration(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }


    static public void setDeaths(Player player, int kills) {
    	config.set("Deaths." + player.getUniqueId(), kills);
  		try {
  			config.save(file);
  			config = YamlConfiguration.loadConfiguration(file);
  		} catch (IOException e) {
  			e.printStackTrace();
  		}
    }
	
    
	static public int getDeaths(Player player) {
		return config.getInt("Deaths." + player.getUniqueId());
    }
	
	static public boolean containsPlayer(Player player) {
		if (config.get("Deaths." + player.getUniqueId()) != null) {
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
