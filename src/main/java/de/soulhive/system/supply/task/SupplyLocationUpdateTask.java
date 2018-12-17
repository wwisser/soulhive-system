package de.soulhive.system.supply.task;

import de.soulhive.system.supply.SupplyService;
import de.soulhive.system.task.ComplexTask;
import de.soulhive.system.util.Config;
import lombok.AllArgsConstructor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

@AllArgsConstructor
public class SupplyLocationUpdateTask extends BukkitRunnable implements ComplexTask {

    private static final long PERIOD = 20L;
    private static final long UPDATE_PERIOD = 1000L * 60 * 60 * 24 * 14; // two weeks

    private SupplyService supplyService;

    @Override
    public void setup(JavaPlugin plugin) {
        super.runTaskTimer(plugin, 0L, PERIOD);

        this.supplyService.getFileStorage().setDefault("lastUpdate", System.currentTimeMillis());
    }

    @Override
    public void run() {
        final long currentTime = System.currentTimeMillis();
        final Config fileStorage = this.supplyService.getFileStorage();
        final long lastUpdate = fileStorage.getLong("lastUpdate");
        final long diff = currentTime - lastUpdate;

        if (diff >= UPDATE_PERIOD) {
            fileStorage.set("lastUpdate", currentTime);
            fileStorage.saveFile();
            this.supplyService.update();
        }
    }

}
