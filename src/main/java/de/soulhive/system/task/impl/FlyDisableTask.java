package de.soulhive.system.task.impl;

import de.soulhive.system.setting.Settings;
import de.soulhive.system.task.ComplexTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class FlyDisableTask extends BukkitRunnable implements ComplexTask {

    private static final long PERIOD = 15L;

    @Override
    public void setup(final JavaPlugin plugin) {
        super.runTaskTimer(plugin, 0, PERIOD);
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers()
            .stream()
            .filter(
                player -> player.getWorld().equals(Settings.WORLD_MAIN)
                    && player.getLocation().getBlockY() < Settings.SPAWN_HEIGHT
                    && !player.hasPermission(Settings.PERMISSION_TEAM)
            )
            .forEach(player -> {
                player.setFlying(false);
                player.setAllowFlight(false);
            });
    }

}
