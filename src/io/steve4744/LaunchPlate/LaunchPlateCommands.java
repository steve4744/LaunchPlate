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
			if (sender instanceof Player) {
				Player player = (Player) sender;

				if (args.length == 0) {
					player.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Version " + version + " : plugin by "+ ChatColor.AQUA + "steve4744");
					return false;
					
				} else if(args[0].equalsIgnoreCase("list")) {
					player.sendMessage(ChatColor.YELLOW + "===============" + ChatColor.GREEN + " LaunchPlate " + ChatColor.YELLOW + "===============");
					player.sendMessage(ChatColor.GREEN + "\nBase block material : " + ChatColor.WHITE + plugin.getSettings().getLaunchBlock());
					player.sendMessage(ChatColor.GREEN + "Pressure plate type : " + ChatColor.WHITE + plugin.getSettings().getPlate());
					player.sendMessage(ChatColor.GREEN + "Launch sound effect : " + ChatColor.WHITE + plugin.getSettings().getSound());
					player.sendMessage(ChatColor.GREEN + "Launch particle effect : " + ChatColor.WHITE + plugin.getSettings().getParticle());
					player.sendMessage(ChatColor.GREEN + "Upward force applied : " + ChatColor.WHITE + plugin.getSettings().getForce());
					player.sendMessage(ChatColor.YELLOW + "\n===============" + ChatColor.GREEN + " LaunchPlate " + ChatColor.YELLOW + "===============");
					return false;
					
				} else if(args[0].equalsIgnoreCase("help")) {
					player.sendMessage(ChatColor.YELLOW + "===============" + ChatColor.GREEN + " LaunchPlate " + ChatColor.YELLOW + "===============");
					player.sendMessage(ChatColor.GREEN + "\n/lp setblock" + ChatColor.YELLOW + " (Material) " + ChatColor.WHITE + "- set the Material for the base block");
					player.sendMessage(ChatColor.GREEN + "/lp setplate" + ChatColor.YELLOW + " (Material) " + ChatColor.WHITE + "- set the pressure plate Material");
					player.sendMessage(ChatColor.GREEN + "/lp setsound" + ChatColor.YELLOW + " (Sound) " + ChatColor.WHITE + "- set a launch sound effect");
					player.sendMessage(ChatColor.GREEN + "/lp settrail" + ChatColor.YELLOW + " (Particle) " + ChatColor.WHITE + "- set a launch particle effect");
					player.sendMessage(ChatColor.GREEN + "/lp setforce" + ChatColor.YELLOW + " (Force) " + ChatColor.WHITE + "- force determines height of bounce");
					player.sendMessage(ChatColor.GREEN + "/lp list " + ChatColor.WHITE + "- list the current LaunchPlate setttings");
					player.sendMessage(ChatColor.GREEN + "/lp reload " + ChatColor.WHITE + "- reload the LaunchPlate config");
					player.sendMessage(ChatColor.GREEN + "/lp help " + ChatColor.WHITE + "- display this command help screen");
					//player.sendMessage(ChatColor.YELLOW + "\n===============" + ChatColor.GREEN + " LaunchPlate " + ChatColor.YELLOW + "===============");
					return false;
				}
				if (!player.hasPermission("launchplate.admin")) {
					player.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Permission required to run commands: " + ChatColor.AQUA + "launchplate.admin");
					return false;
				}
					
				if (args[0].equalsIgnoreCase("reload")) {
					plugin.reloadConfig(); 
					plugin.refreshSettings(new Settings());
					player.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Config Reloaded");
						
				} else if (args[0].equalsIgnoreCase("setsound")) {
					if (args.length >= 2) {
						if (plugin.getCfg().setSound(args[1].toUpperCase())) {
							player.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Sound effect added");
						} else {
							player.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Invalid sound: " + ChatColor.RED + args[1]);
						}
					} else {
						player.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "No sound specified");
					}

				} else if (args[0].equalsIgnoreCase("settrail")) {
					if (args.length >= 2) {
						if (plugin.getCfg().setParticle(args[1].toUpperCase())) {
							player.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Particle effect added");
						} else {
							player.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Invalid particle effect: " + ChatColor.RED + args[1]);
						}
					} else {
						player.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "No particle effect specified");
					}
						
				} else if (args[0].equalsIgnoreCase("setBlock")) {
					if (args.length >= 2) {
						if (Material.getMaterial(args[1].toUpperCase()) != null) {
							plugin.getCfg().setMaterial(args[1].toUpperCase());
							player.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Block material set to " + ChatColor.AQUA + args[1].toUpperCase());
						} else {
							player.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Invalid block material: " + ChatColor.RED + args[1]);
						}
					} else {
						player.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "No block material specified");
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
						if (Settings.isValid(plate)) {
							plugin.getCfg().setPlate(plateMaterial.toUpperCase());
							player.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Plate material set to " + ChatColor.AQUA + args[1].toUpperCase());
						} else {
							player.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Invalid plate material: " + ChatColor.RED + args[1]);
						}
					} else {
						player.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "No plate material specified");
					}	
						
				} else if (args[0].equalsIgnoreCase("setForce")) {
					if (args.length >= 2) {
						if (Settings.isDouble(args[1])) {
							plugin.getCfg().setForce(Double.valueOf(args[1]));
							player.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Force set to " + ChatColor.AQUA + args[1]);
						} else {
							player.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Invalid force: " + ChatColor.RED + args[1]);
						}
					} else {
						player.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "No value specified for force");
					}	
					
				} else {
					player.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Invalid argument - run " + ChatColor.GREEN + "/lp help " + ChatColor.WHITE + "for more information");
				} 
				
			} else {
				if (sender instanceof ConsoleCommandSender) {
					if (args.length >= 1) {
						if (args[0].equalsIgnoreCase("reload")) {
							plugin.reloadConfig();
							plugin.refreshSettings(new Settings());
							plugin.getLogger().info("Config Reloaded");
						} else {
							plugin.getLogger().info("Invalid argument - only 'reload' is supported at this time");
						}
					} else {
						plugin.getLogger().info("Version " + version + " : plugin by steve4744");
					}
				}
			}
		}
		return false;
	}
}
