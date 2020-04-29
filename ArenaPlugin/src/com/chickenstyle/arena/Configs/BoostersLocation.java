package com.chickenstyle.arena.Configs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import com.chickenstyle.arena.Main;



public class BoostersLocation {
	
	private static File file;
	private static YamlConfiguration config;
    public BoostersLocation(Main main) {
  	  file = new File(main.getDataFolder(), "BoosterLocation.yml");
  	 if (!file.exists()) {
  		 try {
				 file.createNewFile();
		    	 config = YamlConfiguration.loadConfiguration(file);
		    	 ArrayList<String> list = new ArrayList<>();
		    	  	config.set("Locations", list);
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
    
	@SuppressWarnings("unchecked")
	static public void addBooster(Location loc) {
		ArrayList<Location> list = (ArrayList<Location>) config.getList("Locations");
		list.add(loc);
		config.set("Locations", list);
		try {
			config.save(file);
			config = YamlConfiguration.loadConfiguration(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	static public Location getRandomLocation() {
		ArrayList<Location> list = (ArrayList<Location>) config.getList("Locations");
		if (!list.isEmpty() ) {
	        Random r= new Random();
	        int randomNumber=r.nextInt(list.size());
	        return list.get(randomNumber);
		}
		return null;

	}
	
	@SuppressWarnings("unchecked")
	public static boolean containsLocation(Location loc) {
		for (Location location:(ArrayList<Location>) config.getList("Locations")) {
			if (location.equals(loc)) {
				return true;
			}
		}
		return false;
	}
    
	@SuppressWarnings("unchecked")
	public static void removeLocation(Location loc) {
		ArrayList<Location> list = (ArrayList<Location>) config.getList("Locations");
		list.remove(loc);
		config.set("Locations", list);
		try {
			config.save(file);
			config = YamlConfiguration.loadConfiguration(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
