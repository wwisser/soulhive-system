package de.soulhive.system.task.impl;

import de.soulhive.system.setting.Settings;
import de.soulhive.system.task.ComplexTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerVoidKillTask extends BukkitRunnable implements ComplexTask {

    private static final long PERIOD = 15L;

    @Override
    public void setup(JavaPlugin plugin) {
        super.runTaskTimer(plugin, 0, PERIOD);
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers()
            .stream()
            .filter(
                player -> !player.hasPermission(Settings.PERMISSION_TEAM)
                    && player.getLocation().getBlockY() <= Settings.VOID_HEIGHT
            )
            .forEach(player -> player.setHealth(0));
    }

}
