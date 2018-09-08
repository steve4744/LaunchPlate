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
	
	public static void setForce(Double force) {
		plugin.getConfig().set("Force", force);
		plugin.saveConfig();
	}
}
