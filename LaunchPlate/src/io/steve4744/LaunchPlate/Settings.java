package io.steve4744.LaunchPlate;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.EnumUtils;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;

import io.steve4744.LaunchPlate.LaunchPlate;

public class Settings {
	
	private LaunchPlate plugin;
		
	private Material material;
	private Material plate;
	private double force;
	private boolean verticalBounce;
	private double magnitude;
	private Sound sound;
	private Particle trail;
	private boolean debug;
		
	private final static Set<Material> values = new HashSet<Material>(Arrays.asList(Material.STONE_PRESSURE_PLATE, Material.OAK_PRESSURE_PLATE, Material.BIRCH_PRESSURE_PLATE, Material.SPRUCE_PRESSURE_PLATE, Material.JUNGLE_PRESSURE_PLATE, Material.DARK_OAK_PRESSURE_PLATE, Material.ACACIA_PRESSURE_PLATE, Material.HEAVY_WEIGHTED_PRESSURE_PLATE, Material.LIGHT_WEIGHTED_PRESSURE_PLATE));
	
	public Settings(LaunchPlate plugin){
		
		this.plugin = plugin;
		FileConfiguration config = this.plugin.getConfig();
		
		material = Material.getMaterial(config.getString("Material").toUpperCase());
		plate = Material.getMaterial(config.getString("Plate").toUpperCase());	
		if (!isValid(plate)) {
			plate = null;
		}
		
		force = config.getDouble("Force");
		magnitude = config.getDouble("Magnitude");
		verticalBounce = config.getBoolean("Vertical_Bounce");
		debug = config.getBoolean("Debug");
		
		if (EnumUtils.isValidEnum(Sound.class, config.getString("Sound"))) {
			sound = Sound.valueOf(config.getString("Sound"));
		}
		if (EnumUtils.isValidEnum(Particle.class, config.getString("Trail"))) {
			trail = Particle.valueOf(config.getString("Trail"));
		}
		
		if (debug) {
			String blockCheck = "[Settings] Material = " + material;
			String plateCheck = "[Settings] Plate = " + plate;
			String forceCheck = "[Settings] Force = " + force;
			String soundCheck = "[Settings] Sound = " + sound;
			String trailCheck = "[Settings] Trail = " + trail;
			plugin.getLogger().info(blockCheck);
			plugin.getLogger().info(plateCheck);
			plugin.getLogger().info(forceCheck);
			plugin.getLogger().info(soundCheck);
			plugin.getLogger().info(trailCheck);
		}
	}
	
	public Material getMaterial() {
		return material;
	}
	public Material getPlate() {
		return plate;
	}
	public double getForce() {
		return force;
	}
	public boolean isVertical() {
		return verticalBounce;
	}
	public double getMagnitude() {
		return magnitude;
	}
	public Sound getSound() {
		return sound;
	}
	public Particle getParticle() {
		return trail;
	}
	public static boolean isValid(Material plate) {
		if (!values.contains(plate)) {
			return false;
		}
		return true;
	}
}
