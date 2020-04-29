package com.chickenstyle.arena.Boosters;

import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Boosters {
	public static void applyBooster(Player player) {
		Random ran = new Random();
		int random = ran.nextInt(8) + 1;
		switch (random) {
		case 1:
			player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, 2));
		break;
		
		case 2:
			player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 300, 2));
		break;
		
		case 3:
			player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 300, 1));
		break;
			
		case 4:
			player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 300, 1));
		break;
		
		case 5:
			player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 300, 0));
		break;

		case 6:
			player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 300, 1));
		break;
		
		case 7:
			player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 300, 1));
		break;
		
		case 8:
			player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 300, 1));
		break;
		}
	}
}
