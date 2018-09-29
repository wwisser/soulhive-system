package de.skydust.system.task.impl;

import de.skydust.system.task.ComplexTask;
import de.skydust.system.util.nms.Tablist;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;

public class TablistUpdateTask extends BukkitRunnable implements ComplexTask {

    private static final List<String> TAB_HEADER = Arrays.asList(
            "",
            "      §9§lSkyDust §8➥ §7Deine §fSkyPvP §7Community",
            ""
    );

    private static final long PERIOD = 20;

    @Override
    public void setup(JavaPlugin plugin) {
        super.runTaskTimer(plugin, 0L, PERIOD);
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach((Player player) -> {
            String footer = "§7Du befindest dich gerade ";
            String worldName = player.getWorld().getName();

            if (worldName.contains("ASkyBlock")) {
                footer += "in der §fSkyBlock §7Welt.";
            } else {
                int blockY = player.getLocation().getBlockY();

                if (blockY < 150) {
                    footer += "in der §fPvP Zone§7.";
                } else {
                    footer += "am §fSpawn§7.";
                }
            }

            Tablist.send(
                    player,
                    String.join("\n", TAB_HEADER),
                    String.join("\n", Arrays.asList("", footer, ""))
            );
        });
    }

}
