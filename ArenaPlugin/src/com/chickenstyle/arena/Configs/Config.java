package com.chickenstyle.arena.Configs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import com.chickenstyle.arena.Main;



public class Config {
	static Main main = Main.getPlugin(Main.class);
	static File file = new File(Main.getPlugin(Main.class).getDataFolder(), "config.yml");
	static YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
	
	//Getters
	public static String getString(String path) {
		return config.getString(path);
	}
	
	public static int getIneger(String path) {
		return config.getInt(path);
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<String> getList(String path) {
		return (ArrayList<String>) config.getList(path);
	}
	public static void reload() {
		try {
			config.save(file);
			main.reloadConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static Location getLobbyLocation() {
		return (Location) config.get("lobbylocation");
	}
	//Setters
	public static void setLobbyLocation(Location location) {
		config.set("lobbylocation", location);
		try {
			config.save(file);
			main.reloadConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
