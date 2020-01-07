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
import org.bukkit.entity.Player;

public class LaunchPlateCommands implements CommandExecutor {

	private final LaunchPlate plugin;
	private final String version;

	public LaunchPlateCommands(LaunchPlate plugin, String version) {
		this.plugin = plugin;
		this.version = version;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("launchplate")) {
			if (sender instanceof Player || sender instanceof ConsoleCommandSender) {

				if (args.length == 0 || args[0].equalsIgnoreCase("info")) {
					sender.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Version " + version + " : plugin by "+ ChatColor.AQUA + "steve4744");
					return false;

				} else if(args[0].equalsIgnoreCase("list")) {
					sender.sendMessage(ChatColor.YELLOW + "===============" + ChatColor.GREEN + " LaunchPlate " + ChatColor.YELLOW + "===============");
					sender.sendMessage(ChatColor.GREEN + "Base block material : " + ChatColor.WHITE + plugin.getSettings().getLaunchBlock());
					sender.sendMessage(ChatColor.GREEN + "Pressure plate type : " + ChatColor.WHITE + plugin.getSettings().getPlate());
					sender.sendMessage(ChatColor.GREEN + "Launch sound effect : " + ChatColor.WHITE + plugin.getSettings().getSound());
					sender.sendMessage(ChatColor.GREEN + "Launch particle effect : " + ChatColor.WHITE + plugin.getSettings().getParticle());
					sender.sendMessage(ChatColor.GREEN + "Upward force applied : " + ChatColor.WHITE + plugin.getSettings().getForce());
					sender.sendMessage(ChatColor.YELLOW + "===============" + ChatColor.GREEN + " LaunchPlate " + ChatColor.YELLOW + "===============");
					return false;

				} else if(args[0].equalsIgnoreCase("help")) {
					String cmd = "/lp";
					if (sender instanceof ConsoleCommandSender) {
						cmd = "lp";
					}
					sender.sendMessage(ChatColor.YELLOW + "===============" + ChatColor.GREEN + " LaunchPlate " + ChatColor.YELLOW + "===============");
					sender.sendMessage(ChatColor.GREEN + cmd + " setblock" + ChatColor.YELLOW + " (Material) " + ChatColor.WHITE + "- set the Material for the base block");
					sender.sendMessage(ChatColor.GREEN + cmd + " setplate" + ChatColor.YELLOW + " (Material) " + ChatColor.WHITE + "- set the pressure plate Material");
					sender.sendMessage(ChatColor.GREEN + cmd + " setsound" + ChatColor.YELLOW + " (Sound) " + ChatColor.WHITE + "- set a launch sound effect");
					sender.sendMessage(ChatColor.GREEN + cmd + " settrail" + ChatColor.YELLOW + " (Particle) " + ChatColor.WHITE + "- set a launch particle effect");
					sender.sendMessage(ChatColor.GREEN + cmd + " setforce" + ChatColor.YELLOW + " (Force) " + ChatColor.WHITE + "- force determines height of bounce");
					sender.sendMessage(ChatColor.GREEN + cmd + " list " + ChatColor.WHITE + "- list the current LaunchPlate setttings");
					sender.sendMessage(ChatColor.GREEN + cmd + " reload " + ChatColor.WHITE + "- reload the LaunchPlate config");
					sender.sendMessage(ChatColor.GREEN + cmd + " help " + ChatColor.WHITE + "- display this command help screen");
					return false;
				}
				if (!sender.hasPermission("launchplate.admin")) {
					sender.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Permission required to run commands: " + ChatColor.AQUA + "launchplate.admin");
					return false;
				}

				if (args[0].equalsIgnoreCase("reload")) {
					plugin.reloadConfig(); 
					plugin.refreshSettings();
					sender.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Config Reloaded");

				} else if (args[0].equalsIgnoreCase("setsound")) {
					if (args.length >= 2) {
						if (plugin.getSettings().setSound(args[1].toUpperCase())) {
							sender.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Sound effect added");
						} else {
							sender.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Invalid sound: " + ChatColor.RED + args[1]);
						}
					} else {
						sender.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "No sound specified");
					}

				} else if (args[0].equalsIgnoreCase("settrail")) {
					if (args.length >= 2) {
						if (plugin.getSettings().setParticle(args[1].toUpperCase())) {
							sender.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Particle effect added");
						} else {
							sender.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Invalid particle effect: " + ChatColor.RED + args[1]);
						}
					} else {
						sender.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "No particle effect specified");
					}

				} else if (args[0].equalsIgnoreCase("setBlock")) {
					if (args.length >= 2) {
						if (Material.getMaterial(args[1].toUpperCase()) != null) {
							plugin.getSettings().setMaterial(args[1].toUpperCase());
							sender.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Block material set to " + ChatColor.AQUA + args[1].toUpperCase());
						} else {
							sender.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Invalid block material: " + ChatColor.RED + args[1]);
						}
					} else {
						sender.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "No block material specified");
					}	

				} else if (args[0].equalsIgnoreCase("setPlate")) {
					if (args.length >= 2) {
						String plateMaterial;
						if (args[1].equalsIgnoreCase("GOLD_PLATE") || args[1].equalsIgnoreCase("GOLD_PRESSURE_PLATE")) {
							plateMaterial = "LIGHT_WEIGHTED_PRESSURE_PLATE";
						} else if (args[1].equalsIgnoreCase("IRON_PLATE") || args[1].equalsIgnoreCase("IRON_PRESSURE_PLATE")) {
							plateMaterial = "HEAVY_WEIGHTED_PRESSURE_PLATE";
						} else {
							plateMaterial = args[1];
						}
						Material plate = Material.getMaterial(plateMaterial.toUpperCase());
						if (plugin.getSettings().isValid(plate)) {
							plugin.getSettings().setPlate(plateMaterial.toUpperCase());
							sender.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Plate material set to " + ChatColor.AQUA + args[1].toUpperCase());
						} else {
							sender.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Invalid plate material: " + ChatColor.RED + args[1]);
						}
					} else {
						sender.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "No plate material specified");
					}	

				} else if (args[0].equalsIgnoreCase("setForce")) {
					if (args.length >= 2) {
						if (plugin.getSettings().isDouble(args[1])) {
							plugin.getSettings().setForce(Double.valueOf(args[1]));
							sender.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Force set to " + ChatColor.AQUA + args[1]);
						} else {
							sender.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Invalid force: " + ChatColor.RED + args[1]);
						}
					} else {
						sender.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "No value specified for force");
					}	

				} else {
					sender.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Invalid argument - run " + ChatColor.GREEN + "/lp help " + ChatColor.WHITE + "for more information");
				} 

			} else {
				plugin.getLogger().info("Version " + version + " : plugin by steve4744");
			}
		}
		return false;
	}
}
