/*
 * MIT License

Copyright (c) 2018 steve4744

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

 */
package io.steve4744.LaunchPlate;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;

import com.google.common.base.Enums;

import io.steve4744.LaunchPlate.LaunchPlate;

public class Settings {

	private final LaunchPlate plugin;
	private FileConfiguration config;

	private final Set<Material> values = new HashSet<Material>(Arrays.asList(Material.STONE_PRESSURE_PLATE, Material.OAK_PRESSURE_PLATE, Material.BIRCH_PRESSURE_PLATE, Material.SPRUCE_PRESSURE_PLATE, Material.JUNGLE_PRESSURE_PLATE, Material.DARK_OAK_PRESSURE_PLATE, Material.ACACIA_PRESSURE_PLATE, Material.HEAVY_WEIGHTED_PRESSURE_PLATE, Material.LIGHT_WEIGHTED_PRESSURE_PLATE));

	public Settings(LaunchPlate plugin) {
		this.plugin = plugin;
		config = plugin.getConfig();
	}

	public Material getLaunchBlock() {
		return Material.getMaterial(config.getString("Material").toUpperCase());
	}

	public Material getPlate() {
		Material plate = Material.getMaterial(config.getString("Plate").toUpperCase());	
		return isValid(plate) ? plate : null;
	}

	public double getForce() {
		return config.getDouble("Force");
	}

	public boolean isVertical() {
		return config.getBoolean("Vertical_Bounce");
	}

	public double getMagnitude() {
		return config.getDouble("Magnitude");
	}

	public Sound getSound() {
		return config.getString("Sound") != null ? Enums.getIfPresent(Sound.class, config.getString("Sound")).orNull() : null;
	}

	public Particle getParticle() {
		return config.getString("Trail") != null ? Enums.getIfPresent(Particle.class, config.getString("Trail")).orNull() : null;
	}

	public boolean isValid(Material plate) {
		return values.contains(plate);
	}

	public boolean isDouble(String text) {
		try {
			Double.parseDouble(text);
			return true;
		} catch (NumberFormatException e) {}
		return false;
	}

	public boolean setSound(String soundEffect) {
		if (Enums.getIfPresent(Sound.class, soundEffect).orNull() != null) {
			plugin.getConfig().set("Sound", soundEffect);
			plugin.saveConfig();
			return true;
		}
		return false;
	}

	public boolean setParticle(String particleEffect) {
		if (Enums.getIfPresent(Particle.class, particleEffect).orNull() != null) {
			plugin.getConfig().set("Trail", particleEffect);
			plugin.saveConfig();
			return true;
		}
		return false;
	}

	public void setMaterial(String material) {
		plugin.getConfig().set("Material", material);
		plugin.saveConfig();
	}

	public void setPlate(String plate) {
		plugin.getConfig().set("Plate", plate);
		plugin.saveConfig();
	}

	public void setForce(Double force) {
		plugin.getConfig().set("Force", force);
		plugin.saveConfig();
	}

}
