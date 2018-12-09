package de.soulhive.system.motd.task;

import de.soulhive.system.motd.MotdService;
import de.soulhive.system.task.ComplexTask;
import lombok.AllArgsConstructor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

@AllArgsConstructor
public class MotdUpdateTask extends BukkitRunnable implements ComplexTask {

    private static final long PERIOD = 20L * 60 * 3;

    private MotdService motdService;

    @Override
    public void setup(JavaPlugin plugin) {
        super.runTaskTimer(plugin, 0L, PERIOD);
    }

    @Override
    public void run() {
        this.motdService.reloadConfig();
    }

}
