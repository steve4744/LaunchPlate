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

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import io.github.steve4744.launchplate.metrics.Metrics;

public class LaunchPlate extends JavaPlugin implements Listener {

	private String version;
	private Settings settings;
	private static LaunchPlate instance;
	private static final int SPIGOT_ID = 42251;
	private static final int BSTATS_PLUGIN_ID = 2145;

	@Override
	public void onEnable() {

		this.saveDefaultConfig();

		instance = this;
		version = this.getDescription().getVersion();
		settings = new Settings(this);
		getCommand("launchplate").setExecutor(new LaunchPlateCommands(this, version));
		getCommand("launchplate").setTabCompleter(new AutoTabCompleter());

		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(this, this);

		checkForUpdate();
		new Metrics(this, BSTATS_PLUGIN_ID);
	}

	@Override
	public void onDisable() {
		settings = null;
		getLogger().info((new StringBuilder("LaunchPlate Version ")).append(version).append("....disabled!").toString());
	}

	private void setMD(Player player, String name, Object value) {
		player.setMetadata(name, new FixedMetadataValue(this, value));
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent m) {
		Player player = m.getPlayer();

		if (!player.hasPermission("launchplate.use"))
			return;

		Block block = m.getTo().getBlock();
		if (block.getRelative(BlockFace.DOWN).getType() != getSettings().getLaunchBlock() || block.getType() != getSettings().getPlate()) {
			return;
		}

		double force = getSettings().getForce();
		if (getSettings().isVertical()) {
			// launch the player - force applied will be 4.0 max
			player.setVelocity(new Vector(player.getVelocity().getX(), force, player.getVelocity().getZ()));

			// if the force is > max allowed, then re-launch player after 10 ticks
			if (force > 4.0) {
				new BukkitRunnable() {
					boolean reLaunch = true;
					double force = getSettings().getForce();
					double newForce = 4.0;  // max force
					@Override
					public void run() {
						if (!reLaunch || !player.isOnline()) {
							this.cancel();
							return;
						}
						if (force <= 6.0 ) {
							newForce = force - 2;
							reLaunch = false;
						}
						// keep re-launching player until is newForce is < 4.0
						player.setVelocity(new Vector(player.getVelocity().getX(), newForce, player.getVelocity().getZ()));
						force = force - 2;
					}
				}.runTaskTimer(this, 10L, 10L);
			}
		} else {
			Vector velocity = player.getLocation().getDirection().normalize();
			velocity.setY(1.0);
			player.setVelocity(velocity.multiply(force));
		}

		if (getSettings().getSound() != null) {
			player.getWorld().playSound(player.getLocation(), getSettings().getSound(), 5.0F, 1F);
		}

		//on a double bounce, this has to come after the damage event
		new BukkitRunnable() {
			@Override
			public void run() {
				setMD(player,"noFall",true);
			}
		}.runTask(this);

		Particle p = getSettings().getParticle();
		if (p == null) {
			return;
		}

		DustOptions data = null;
		if (p.getDataType() == Particle.DustOptions.class) {
			data = new Particle.DustOptions(getSettings().getParticleColour("Start"), 1.5F);

		} else if (p.getDataType() == Particle.DustTransition.class) {
			data = new Particle.DustTransition(getSettings().getParticleColour("Start"), getSettings().getParticleColour("End"), 1.5F);

		} else if (p.getDataType() != Void.class) {
			return;
		}

		Location loc = player.getLocation().add(0, 0.5, 0);
		player.getWorld().spawnParticle(getSettings().getParticle(), loc, 50, data);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDamage(EntityDamageEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		Player p = (Player) e.getEntity();
		if (e.getCause() == DamageCause.FALL) {
			if (p.hasMetadata("noFall")) {
				boolean noFall = p.getMetadata("noFall").get(0).asBoolean();
				if (noFall == true) {
					e.setCancelled(true);
				}
				setMD(p,"noFall",false);
			}		
		}
	}

	private void checkForUpdate() {
		if (!getConfig().getBoolean("Check_For_Update", true)) {
			return;
		}
		new VersionChecker(this, SPIGOT_ID).getVersion(latestVersion -> {
			if (version.equals(latestVersion)) {
				getLogger().info("You are running the most recent version");

			} else if (version.contains("beta") || version.toLowerCase().contains("snapshot")) {
				getLogger().info("You are running dev build: " + version);
				getLogger().info("Latest release: " + latestVersion);

			} else if (Character.isDigit(latestVersion.charAt(0))) {
				getLogger().info("Current version: " + version);
				getLogger().info("Latest release: " + latestVersion);
				getLogger().info("Latest release available from Spigot: https://www.spigotmc.org/resources/LaunchPlate." + SPIGOT_ID + "/");
			}
		});
	}

	public Settings getSettings() {
		return settings;
	}

	public void refreshSettings() {
		this.settings = new Settings(this);
	}

	public static LaunchPlate getInstance() {
		return instance;
	}
}
