package de.soulhive.system.stats.tasks;

import de.soulhive.system.task.ComplexTask;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

@AllArgsConstructor
public class PlayerRespawnTask extends BukkitRunnable implements ComplexTask {

    private Player player;

    @Override
    public void setup(JavaPlugin plugin) {
        super.runTaskLater(plugin, 3L);
    }

    @Override
    public void run() {
        this.player.spigot().respawn();
    }
}
