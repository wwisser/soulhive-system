package de.soulhive.system.particle.task;

import de.soulhive.system.particle.ParticleService;
import de.soulhive.system.task.ComplexTask;
import de.soulhive.system.util.nms.ParticleUtils;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

@AllArgsConstructor
public class ParticleApplierTask extends BukkitRunnable implements ComplexTask {

    private static final long PERIOD = 5L;

    private ParticleService particleService;

    @Override
    public void setup(JavaPlugin plugin) {
        super.runTaskTimer(plugin, 0, PERIOD);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            this.particleService.getSelectedParticle(player).ifPresent(particle ->
                ParticleUtils.play(player.getLocation(), particle.getEnumParticle(), 0, 0, 0, 0, 0)
            );
        }
    }

}
