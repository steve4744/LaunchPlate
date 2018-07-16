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
				} else if(args[0].equalsIgnoreCase("help")) {
					player.sendMessage(ChatColor.YELLOW + "===============" + ChatColor.GREEN + " LaunchPlate " + ChatColor.YELLOW + "===============");
					player.sendMessage(ChatColor.GREEN + "\n/lp setblock" + ChatColor.YELLOW + " (Material) " + ChatColor.WHITE + "- set the Material for the base block");
					player.sendMessage(ChatColor.GREEN + "/lp setplate" + ChatColor.YELLOW + " (Material) " + ChatColor.WHITE + "- set the pressure plate Material");
					player.sendMessage(ChatColor.GREEN + "/lp setsound" + ChatColor.YELLOW + " (Sound) " + ChatColor.WHITE + "- set a launch sound effect");
					player.sendMessage(ChatColor.GREEN + "/lp settrail" + ChatColor.YELLOW + " (Particle) " + ChatColor.WHITE + "- set a launch particle effect");
					player.sendMessage(ChatColor.GREEN + "/lp reload " + ChatColor.WHITE + "- config reload, required after above commands");
					player.sendMessage(ChatColor.GREEN + "/lp help " + ChatColor.WHITE + "- display this command help screen");
					player.sendMessage(ChatColor.YELLOW + "\n===============" + ChatColor.GREEN + " LaunchPlate " + ChatColor.YELLOW + "===============");
					return false;
				}
				if (!player.hasPermission("launchplate.admin")) {
					player.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Permission required to run commands: " + ChatColor.GOLD + "launchplate.admin");
					return false;
				}
					
				if (args[0].equalsIgnoreCase("reload")) {
					plugin.reloadConfig();
					plugin.setSettings(new Settings(plugin));
					player.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Config Reloaded");
						
				} else if (args[0].equalsIgnoreCase("setsound")) {
					if (args.length >= 2) {
						if (SetupConfig.setSound(args[1].toUpperCase())) {
							player.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Sound effect added");
						} else {
							player.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Invalid sound: " + args[1]);
						}
					} else {
						player.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "No sound specified");
					}

				} else if (args[0].equalsIgnoreCase("settrail")) {
					if (args.length >= 2) {
						if (SetupConfig.setParticle(args[1].toUpperCase())) {
							player.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Particle effect added");
						} else {
							player.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Invalid particle effect: " + args[1]);
						}
					} else {
						player.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "No particle effect specified");
					}
						
				} else if (args[0].equalsIgnoreCase("setBlock")) {
					if (args.length >= 2) {
						if (Material.getMaterial(args[1].toUpperCase()) != null) {
							SetupConfig.setMaterial(args[1].toUpperCase());
							player.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Block material set to " + args[1].toUpperCase());
						} else {
							player.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Invalid block material: " + args[1]);
						}
					} else {
						player.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "No block material specified");
					}	
					
				} else if (args[0].equalsIgnoreCase("setPlate")) {
					if (args.length >= 2) {
						Material plate = Material.getMaterial(args[1].toUpperCase());
						if (Settings.isValid(plate)) {
							SetupConfig.setPlate(args[1].toUpperCase());
							player.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Plate material set to " + args[1].toUpperCase());
						} else {
							player.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Invalid plate material: " + args[1]);
						}
					} else {
						player.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "No plate material specified");
					}	
						
				} else {
					player.sendMessage(ChatColor.GREEN + "[LaunchPlate] " + ChatColor.WHITE + "Invalid argument - run /lp help for more information");
				}
				
			} else {
				if (sender instanceof ConsoleCommandSender) {
					if (args.length >= 1) {
						if (args[0].equalsIgnoreCase("reload")) {
							plugin.reloadConfig();
							plugin.setSettings(new Settings(plugin));
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
