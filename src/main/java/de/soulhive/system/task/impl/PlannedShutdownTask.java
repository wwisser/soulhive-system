package de.soulhive.system.task.impl;

import de.soulhive.system.SoulHive;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.task.ComplexTask;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlannedShutdownTask extends BukkitRunnable implements ComplexTask {

    private static final long PERIOD = 20L;
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm");
    private static final String TIME_WARNING = "04:55";
    private static final String TIME_SHUTDOWN = "05:00";

    @Override
    public void setup(JavaPlugin plugin) {
        super.runTaskTimer(plugin, PERIOD, PERIOD);
    }

    @Override
    public void run() {
        String time = DATE_FORMAT.format(new Date());

        switch (time) {
            case TIME_WARNING:
                Bukkit.broadcastMessage(Settings.PREFIX + "§cAutomatischer Serverneustart in 5 Minuten.");
                break;
            case TIME_SHUTDOWN:
                Bukkit.broadcastMessage(Settings.PREFIX + "§4Der Server startet jetzt neu.");
                Bukkit.getWorlds().forEach(World::save);
                Bukkit.getServer().shutdown();
                break;
        }
    }

}
