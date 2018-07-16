package io.steve4744.LaunchPlate;

import org.bukkit.Bukkit;
import org.bukkit.Location;
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
import io.steve4744.LaunchPlate.Settings;
import io.steve4744.LaunchPlate.Metrics.Metrics;

public class LaunchPlate extends JavaPlugin implements Listener {
	
	private String version;
	private Settings setting;
		
	@Override
	public void onEnable() {

		// Save a copy of the default config.yml if not exists
		this.saveDefaultConfig();

	    version = this.getDescription().getVersion();
	    getLogger().info((new StringBuilder("LaunchPlate Version ")).append(version).append("....enabled!").toString());
	    
	    this.setting = new Settings(this);
	    new SetupConfig(this);
	    
	    this.getCommand("launchplate").setExecutor(new LaunchPlateCommands(this, version));
	    	    
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(this, this);
		
		checkForUpdate();
		
		@SuppressWarnings("unused")
		Metrics metrics = new Metrics(this);
	}
		
	@Override
	public void onDisable() {
		getLogger().info((new StringBuilder("LaunchPlate Version ")).append(version).append("....disabled!").toString());
	}
		
	public void setMD(Player player, String name, Object value) {
		player.setMetadata(name, new FixedMetadataValue(this, value));
	}
		
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent m) {
		Player player = m.getPlayer();
		Block block = m.getTo().getBlock();
			
		if (!player.hasPermission("launchplate.use"))
			return;
		
		double force = getSettings().getForce();

		if (block.getRelative(BlockFace.DOWN).getType() == getSettings().getMaterial() && block.getType() == getSettings().getPlate()) {
			if (getSettings().isVertical()) {
				// launch the player - force applied will be 4.0 max
				player.setVelocity(new Vector(player.getVelocity().getX(), player.getVelocity().getY() + force, player.getVelocity().getZ()));
				
				// if the force is > max allowed, then re-launch player after 10 ticks
				if (force > 4.0) {
					new BukkitRunnable() {
						boolean reLaunch = true;
						double force = getSettings().getForce();
						double newForce = 4.0;  // max force
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
				player.setVelocity(velocity.multiply((double)getSettings().getMagnitude()));
			}
			
			if (getSettings().getSound() != null) {
				player.getWorld().playSound(player.getLocation(), getSettings().getSound(), 5.0F, 1F);
			}
			
			setMD(player,"noFall",true);
			
			if (getSettings().getParticle() != null) {
				Location loc = player.getLocation().add(0, 0.5, 0);
				player.getWorld().spawnParticle(getSettings().getParticle(), loc, 50); 
			}
		} 
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDamage(EntityDamageEvent d) {
		if (d.getEntity() instanceof Player) {
			Player p = (Player) d.getEntity();
			if (d.getCause() == DamageCause.FALL) {
				if (p.hasMetadata("noFall")) {
					boolean noFall = p.getMetadata("noFall").get(0).asBoolean();
					if (noFall == true) {
						d.setCancelled(true);
					}
					setMD(p,"noFall",false);
				}		
			}
		}
	}
	
	public void checkForUpdate() {
		if(!getConfig().getBoolean("Check_For_Update", true)){
			return;
		}
		Bukkit.getScheduler().runTaskLaterAsynchronously(this, new Runnable() {
			public void run() {
				String latestVersion = VersionChecker.getVersion();
				if (latestVersion == "error") {
					getLogger().info("Error attempting to check for new version. Please report it here: https://www.spigotmc.org/threads/launch-plate.248053/");
				} else {
					if (!version.equals(latestVersion)) {
						getLogger().info("New version " + latestVersion + " available on Spigot: https://www.spigotmc.org/resources/launch-plate.42251/");
					}
				}
			}
		}, 40L);
	}

	public Settings getSettings() {
		return setting;
	}
	public void setSettings(Settings setting) {
		this.setting = setting;
	}
}
