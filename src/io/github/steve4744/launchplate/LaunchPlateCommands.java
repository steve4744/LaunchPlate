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

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class LaunchPlateCommands implements CommandExecutor {

	private final LaunchPlate plugin;
	private final String version;
	private FileConfiguration cfg;

	public LaunchPlateCommands(LaunchPlate plugin, String version) {
		this.plugin = plugin;
		this.version = version;
		cfg = plugin.getSettings().getStringData();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player || sender instanceof ConsoleCommandSender) {

			if (args.length == 0 || args[0].equalsIgnoreCase("info")) {
				sender.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Version " + version + " : plugin by "+ ChatColor.AQUA + "steve4744");
				return true;

			} else if(args[0].equalsIgnoreCase("list")) {
				sender.sendMessage(ChatColor.YELLOW + "===============" + ChatColor.GREEN + " LaunchPlate " + ChatColor.YELLOW + "===============");
				sender.sendMessage(ChatColor.GREEN + "Base block material : " + ChatColor.WHITE + plugin.getSettings().getLaunchBlock());
				sender.sendMessage(ChatColor.GREEN + "Pressure plate type : " + ChatColor.WHITE + plugin.getSettings().getPlate());
				sender.sendMessage(ChatColor.GREEN + "Launch sound effect : " + ChatColor.WHITE + plugin.getSettings().getSound());
				sender.sendMessage(ChatColor.GREEN + "Launch particle effect : " + ChatColor.WHITE + plugin.getSettings().getParticle());
				sender.sendMessage(ChatColor.GREEN + "Force applied : " + ChatColor.WHITE + plugin.getSettings().getForce());
				sender.sendMessage(ChatColor.GREEN + "Vertical launch : " + ChatColor.WHITE + String.valueOf(plugin.getSettings().isVertical()));
				sender.sendMessage(ChatColor.YELLOW + "===============" + ChatColor.GREEN + " LaunchPlate " + ChatColor.YELLOW + "===============");
				return true;

			} else if(args[0].equalsIgnoreCase("help")) {
				sender.sendMessage(ChatColor.YELLOW + "===============" + ChatColor.GREEN + " LaunchPlate " + ChatColor.YELLOW + "===============");
				if (sender instanceof Player) {
					Utils.displayHelp((Player) sender);
					return true;
				}
				sender.sendMessage(ChatColor.GREEN + "lpl setblock" + ChatColor.YELLOW + " (Material) " + ChatColor.WHITE + "- " + cfg.getString("help.setblock"));
				sender.sendMessage(ChatColor.GREEN + "lpl setplate" + ChatColor.YELLOW + " (Material) " + ChatColor.WHITE + "- " + cfg.getString("help.setplate"));
				sender.sendMessage(ChatColor.GREEN + "lpl setsound" + ChatColor.YELLOW + " (Sound) " + ChatColor.WHITE + "- " + cfg.getString("help.setsound"));
				sender.sendMessage(ChatColor.GREEN + "lpl settrail" + ChatColor.YELLOW + " (Particle) [colour] " + ChatColor.WHITE + "- " + cfg.getString("help.settrail"));
				sender.sendMessage(ChatColor.GREEN + "lpl setforce" + ChatColor.YELLOW + " (Force) " + ChatColor.WHITE + "- " + cfg.getString("help.setforce"));
				sender.sendMessage(ChatColor.GREEN + "lpl setvertical" + ChatColor.YELLOW + " (True|False) " + ChatColor.WHITE + "- " + cfg.getString("help.setvertical"));
				sender.sendMessage(ChatColor.GREEN + "lpl list " + ChatColor.WHITE + "- " + cfg.getString("help.list"));
				sender.sendMessage(ChatColor.GREEN + "lpl reload " + ChatColor.WHITE + "- " + cfg.getString("help.reload"));
				sender.sendMessage(ChatColor.GREEN + "lpl help " + ChatColor.WHITE + "- " + cfg.getString("help.help"));
				return true;
			}
			if (!sender.hasPermission("launchplate.admin")) {
				sender.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Permission required to run command: " + ChatColor.AQUA + "launchplate.admin");
				return false;
			}

			if (args[0].equalsIgnoreCase("reload")) {
				plugin.reloadConfig(); 
				plugin.refreshSettings();
				sender.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Config Reloaded");
				return true;
			}

			if (args.length < 2) {
				sender.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Missing argument - run " + ChatColor.GREEN + "/lpl help " + ChatColor.WHITE + "for usage");
				return false;
			}

			if (args[0].equalsIgnoreCase("setsound")) {
				if (plugin.getSettings().setSound(args[1])) {
					sender.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Sound effect " + ChatColor.AQUA + args[1] + ChatColor.WHITE + " added");
					return true;
				} else {
					sender.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Invalid sound: " + ChatColor.RED + args[1]);
				}

			} else if (args[0].equalsIgnoreCase("settrail")) {
				if (plugin.getSettings().setParticle(args[1])) {
					sender.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Particle effect " + ChatColor.AQUA + args[1] + ChatColor.WHITE + " added");
					if (args.length > 2 && args[2] != null) {
						plugin.getSettings().setParticleColour(args[2], "Start");
					}
					if (args.length > 3 && args[3] != null) {
						plugin.getSettings().setParticleColour(args[3], "End");
					}
					return true;
				} else {
					sender.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Invalid particle effect: " + ChatColor.RED + args[1]);
				}

			} else if (args[0].equalsIgnoreCase("setBlock")) {
				if (Material.getMaterial(args[1].toUpperCase()) != null) {
					plugin.getSettings().setMaterial(args[1].toUpperCase());
					sender.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Block material set to " + ChatColor.AQUA + args[1].toUpperCase());
					return true;
				} else {
					sender.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Invalid block material: " + ChatColor.RED + args[1]);
				}	

			} else if (args[0].equalsIgnoreCase("setPlate")) {
				String plateMaterial;
				if (args[1].equalsIgnoreCase("GOLD_PLATE") || args[1].equalsIgnoreCase("GOLD_PRESSURE_PLATE")) {
					plateMaterial = "LIGHT_WEIGHTED_PRESSURE_PLATE";
				} else if (args[1].equalsIgnoreCase("IRON_PLATE") || args[1].equalsIgnoreCase("IRON_PRESSURE_PLATE")) {
					plateMaterial = "HEAVY_WEIGHTED_PRESSURE_PLATE";
				} else {
					plateMaterial = args[1];
				}
				Material plate = Material.getMaterial(plateMaterial.toUpperCase());
				if (plate != null && plugin.getSettings().isValid(plate)) {
					plugin.getSettings().setPlate(plateMaterial.toUpperCase());
					sender.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Plate material set to " + ChatColor.AQUA + args[1].toUpperCase());
					return true;
				} else {
					sender.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Invalid plate material: " + ChatColor.RED + args[1]);
				}	

			} else if (args[0].equalsIgnoreCase("setForce")) {
				if (Utils.isDouble(args[1])) {
					plugin.getSettings().setForce(Double.valueOf(args[1]));
					sender.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Force set to " + ChatColor.AQUA + args[1]);
					return true;
				} else {
					sender.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Invalid force: " + ChatColor.RED + args[1]);
				}

			} else if (args[0].equalsIgnoreCase("setVertical")) {
				if (args[1].equalsIgnoreCase("true") || args[1].equalsIgnoreCase("false")) {
					plugin.getSettings().setVertical(args[1]);
					sender.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Vertical launch set to " + ChatColor.AQUA + args[1]);
					return true;
				} else {
					sender.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Invalid option: " + ChatColor.RED + args[1]);
				}

			} else {
				sender.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Invalid argument - run " + ChatColor.GREEN + "/lpl help " + ChatColor.WHITE + "for usage");
			}
		}
		return false;
	}
}
