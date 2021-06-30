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
package io.github.steve4744.launchplate;

import java.io.File;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.Tag;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.google.common.base.Enums;

public class Settings {

	private final LaunchPlate plugin;
	private FileConfiguration config;
	private File dataFolder;
	private File stringFile;
	private YamlConfiguration stringData;

	public Settings(LaunchPlate plugin) {
		this.plugin = plugin;
		config = plugin.getConfig();
		dataFolder = plugin.getDataFolder();
		stringFile = new File(dataFolder, "strings.yml");
		stringData = new YamlConfiguration();

		if (!stringFile.exists()) {
			try {
				stringFile.createNewFile();
				plugin.getLogger().info("Created strings.yml");
			} catch (Exception ex) {
				ex.printStackTrace();
				plugin.getLogger().info("Failed!");
			}
		}
		reloadStrings();
		saveStrings();
	}

	public void reloadStrings() {
		try {
			stringData.load(stringFile);

		} catch (Exception ex) {
			plugin.getLogger().info("Failed loading config: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public FileConfiguration getStringData() {
		return stringData;
    }

	public void saveStrings() {
		try{
			stringData.addDefault("help.setblock", "set the base block Material");
			stringData.addDefault("help.setplate", "set the pressure plate Material");
			stringData.addDefault("help.setsound", "set a launch sound effect");
			stringData.addDefault("help.settrail", "set a launch particle effect");
			stringData.addDefault("help.setforce", "numeric value to determine height");
			stringData.addDefault("help.setvertical", "set vertical launch");
			stringData.addDefault("help.list", "list the current LaunchPlate setttings");
			stringData.addDefault("help.reload", "reload the LaunchPlate config");
			stringData.addDefault("help.help", "display this command help screen");
			stringData.options().copyDefaults(true);
			stringData.save(stringFile);
			reloadStrings();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
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

	public Sound getSound() {
		return config.getString("Sound") != null ? Enums.getIfPresent(Sound.class, config.getString("Sound")).orNull() : null;
	}

	public Particle getParticle() {
		return config.getString("Trail") != null ? Enums.getIfPresent(Particle.class, config.getString("Trail")).orNull() : null;
	}

	public boolean isValid(Material plate) {
		return Tag.PRESSURE_PLATES.isTagged(plate);
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

	public void setVertical(String vertical) {
		plugin.getConfig().set("Vertical_Bounce", Boolean.valueOf(vertical));
		plugin.saveConfig();
	}

}
