package io.steve4744.LaunchPlate;

import org.apache.commons.lang3.EnumUtils;
import org.bukkit.Particle;
import org.bukkit.Sound;

public class SetupConfig {
	
	private static LaunchPlate plugin;
	
	public SetupConfig(LaunchPlate pl) {
		plugin = pl;
	}
	
	public static boolean setSound(String soundEffect) {
		if (EnumUtils.isValidEnum(Sound.class, soundEffect)) {
			plugin.getConfig().set("Sound", soundEffect);
			plugin.saveConfig();
			return true;
		}
		return false;
	}
	
	public static boolean setParticle(String particleEffect) {
		if (EnumUtils.isValidEnum(Particle.class, particleEffect)) {
			plugin.getConfig().set("Trail", particleEffect);
			plugin.saveConfig();
			return true;
		}
		return false;
	}
	
	public static void setMaterial(String material) {
		plugin.getConfig().set("Material", material);
		plugin.saveConfig();
	}
	
	public static void setPlate(String plate) {
		plugin.getConfig().set("Plate", plate);
		plugin.saveConfig();
	}
}
