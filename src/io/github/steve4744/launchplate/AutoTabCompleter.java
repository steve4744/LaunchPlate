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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Registry;
import org.bukkit.Tag;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class AutoTabCompleter implements TabCompleter {

	private static final List<String> COMMANDS = Arrays.asList("help", "list");

	private static final List<String> ADMIN_COMMANDS = Arrays.asList(
			"setblock", "setplate", "settrail", "setsound", "setforce", "setvertical", "reload");

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		if (!(sender instanceof Player)) {
			return null;
		}

		List<String> list = new ArrayList<>();
		List<String> auto = new ArrayList<>();

		if (args.length == 1) {
			list.addAll(COMMANDS);
			if (sender.hasPermission("launchplate.admin")) {
				list.addAll(ADMIN_COMMANDS);
			}

		} else if (args.length == 2 && sender.hasPermission("launchplate.admin")) {
			if (args[0].equalsIgnoreCase("setsound")) {
				Registry.SOUNDS.forEach(sound -> {
					list.add(String.valueOf(sound));
				});
				list.add("none");

			} else if (args[0].equalsIgnoreCase("settrail")) {
				Registry.PARTICLE_TYPE.forEach(particle -> {
					list.add(String.valueOf(particle));
				});
				list.add("none");

			} else if (args[0].equalsIgnoreCase("setplate")) {
				for (Material plate : Tag.PRESSURE_PLATES.getValues()) {
					list.add(plate.toString());
				}

			} else if (args[0].equalsIgnoreCase("setvertical")) {
				list.add("true");
				list.add("false");
			}

		} else if (args.length == 3 && sender.hasPermission("launchplate.admin")) {
			if (args[0].equalsIgnoreCase("settrail") && (args[1].equalsIgnoreCase("REDSTONE") || args[1].equalsIgnoreCase("DUST_COLOR_TRANSITION"))) {
				list.addAll(LaunchPlate.getInstance().getSettings().getColours().keySet());
			}
		} else if (args.length == 4 && sender.hasPermission("launchplate.admin")) {
			if (args[0].equalsIgnoreCase("settrail") && args[1].equalsIgnoreCase("DUST_COLOR_TRANSITION")) {
				list.addAll(LaunchPlate.getInstance().getSettings().getColours().keySet());
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
