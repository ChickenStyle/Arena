package com.chickenstyle.arena.Configs;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.chickenstyle.arena.Main;



public class Kills {
	
	private static File file;
	private static YamlConfiguration config;
    public Kills(Main main) {
  	  file = new File(main.getDataFolder(), "Kills.yml");
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
    static public void addKill(Player player) {
    		config.set("kills." + player.getUniqueId(), config.getInt("kills." + player.getUniqueId().toString()) + 1);
    	  	try {
    			config.save(file);
    	    	config = YamlConfiguration.loadConfiguration(file);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    }
  
    
    static public void setKills(Player player, int kills) {
    	config.set("kills." + player.getUniqueId(), kills);
	  	try {
			config.save(file);
	    	config = YamlConfiguration.loadConfiguration(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	static public int getKills(Player player) {
		return config.getInt("kills." + player.getUniqueId());
    }
	
	static public boolean containsPlayer(Player player) {
		if (config.get("kills." + player.getUniqueId()) != null) {
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
