/*
 * MIT License

Copyright (c) 2020 steve4744

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

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Content;
import net.md_5.bungee.api.chat.hover.content.Text;

public class Utils {

	private static FileConfiguration cfg = LaunchPlate.getInstance().getSettings().getStringData();
	
	public static boolean isDouble(String text) {
		try {
			Double.parseDouble(text);
			return true;
		} catch (NumberFormatException e) {}
		return false;
	}

	public static void displayHelp(Player player) {
		player.spigot().sendMessage(getTextComponent("/lpl setblock [material]", true), getTextComponent(cfg.getString("help.setblock")));
		player.spigot().sendMessage(getTextComponent("/lpl setplate [material]", true), getTextComponent(cfg.getString("help.setplate")));
		player.spigot().sendMessage(getTextComponent("/lpl setsound [sound]", true), getTextComponent(cfg.getString("help.setsound")));
		player.spigot().sendMessage(getTextComponent("/lpl settrail [particle]", true), getTextComponent(cfg.getString("help.settrail")));
		player.spigot().sendMessage(getTextComponent("/lpl setforce [force]", true), getTextComponent(cfg.getString("help.setforce")));
		player.spigot().sendMessage(getTextComponent("/lpl setvertical [true | false]", true), getTextComponent(cfg.getString("help.setvertical")));
		player.spigot().sendMessage(getTextComponent("/lpl list", true), getTextComponent(cfg.getString("help.list")));
		player.spigot().sendMessage(getTextComponent("/lpl reload", true), getTextComponent(cfg.getString("help.reload")));
		player.spigot().sendMessage(getTextComponent("/lpl help", true), getTextComponent(cfg.getString("help.help")));
	}

	private static TextComponent getTextComponent(String text) {
		return getTextComponent(text, false);
	}

	private static TextComponent getTextComponent(String text, Boolean isClickable) {
		String splitter = "[";
		TextComponent cmd = new TextComponent(getSubstringBefore(text, splitter));
		if (isClickable) {
			cmd.setColor(ChatColor.GREEN);
			cmd.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, getSubstringBefore(text, splitter)));

			Content content = new Text(new ComponentBuilder("Click to select").create());
			cmd.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, content));

			if (getSubstringAfter(text, splitter).length() != 0) {
				TextComponent arg = new TextComponent(splitter + getSubstringAfter(text, splitter));
				arg.setColor(ChatColor.YELLOW);
				cmd.addExtra(arg);
			}
			cmd.addExtra(getTextComponentDelimiter(" - "));
		} else {
			cmd.setColor(ChatColor.WHITE);
		}
		return cmd;
	}

	private static TextComponent getTextComponentDelimiter(String delim) {
		TextComponent tc = new TextComponent(delim);
		tc.setColor(ChatColor.GRAY);
		return tc;
	}

	private static String getSubstringBefore(String text, String splitter) {
		int index = text.indexOf(splitter);
		return index == -1 ? text : text.substring(0, index);
	}

	private static String getSubstringAfter(String text, String splitter) {
		int index = text.indexOf(splitter);
		return index == -1 ? "" : text.substring(index + 1);
	}
}
