package de.soulhive.system.task.impl;

import de.soulhive.system.task.ComplexTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class IslandLevelToplistUpdateTask extends BukkitRunnable implements ComplexTask {

    private static final long PERIOD = 20L * 60 * 10;

    @Override
    public void setup(JavaPlugin plugin) {
        super.runTaskTimer(plugin, 0, PERIOD);
    }

    @Override
    public void run() {
        if (Bukkit.getOnlinePlayers().size() > 0) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "asadmin topten");
        }
    }

}
