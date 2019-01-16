package de.soulhive.system.task.impl;

import de.soulhive.system.setting.Settings;
import de.soulhive.system.task.ComplexTask;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class JumpboostSupplierTask extends BukkitRunnable implements ComplexTask {

    private static final long PERIOD = 3L;

    @Override
    public void setup(JavaPlugin plugin) {
        super.runTaskTimer(plugin, 0L, PERIOD);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!player.getWorld().equals(Settings.WORLD_MAIN)
                || player.getLocation().add(0, -1, 0).getBlock().getType() != Material.SLIME_BLOCK) {
                continue;
            }

            player.setVelocity(player.getLocation().getDirection().setY(0.3).multiply(2.5));
            player.playSound(player.getLocation(), Sound.SLIME_ATTACK, 1, 1);
        }
    }

}
