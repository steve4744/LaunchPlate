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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class AutoTabCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		if (!(sender instanceof Player)) {
			return null;
		}

		List<String> list = new ArrayList<String>();
		List<String> auto = new ArrayList<String>();

		if (args.length == 1) {
			list.add("help");
			list.add("list");
			if (sender.hasPermission("launchplate.admin")) {
				list.add("setblock");
				list.add("setplate");
				list.add("settrail");
				list.add("setsound");
				list.add("setforce");
				list.add("reload");
			}
		} else if (args.length == 2 && sender.hasPermission("launchplate.admin")) {
			if (args[0].equalsIgnoreCase("setsound")) {
				for (Sound sound : Arrays.asList(Sound.class.getEnumConstants())) {
					list.add(String.valueOf(sound));
				}
			} else if (args[0].equalsIgnoreCase("settrail")) {
				for (Particle particle : Arrays.asList(Particle.class.getEnumConstants())) {
					list.add(String.valueOf(particle));
				}
			} else if (args[0].equalsIgnoreCase("setplate")) {
				list.add("iron_plate");
				list.add("gold_plate");
				list.add("oak_pressure_plate");
				list.add("dark_oak_pressure_plate");
				list.add("birch_pressure_plate");
				list.add("jungle_pressure_plate");
				list.add("spruce_pressure_plate");
				list.add("acacia_pressure_plate");
				list.add("stone_pressure_plate");
				list.add("light_weighted_pressure_plate");
				list.add("heavy_weighted_pressure_plate");
			}
		}
		for (String s : list) {
			if (s.startsWith(args[args.length - 1])) {
				auto.add(s);
			}
		}
		return auto.isEmpty() ? list : auto;
	}

}
