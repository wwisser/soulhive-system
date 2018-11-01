package de.soulhive.system.task.impl;

import de.soulhive.system.setting.Settings;
import de.soulhive.system.task.ComplexTask;
import de.soulhive.system.util.nms.Tablist;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;

public class TablistUpdateTask extends BukkitRunnable implements ComplexTask {

    private static final long PERIOD = 20;
    private static final List<String> TAB_HEADER = Arrays.asList(
        "",
        "      §9§lSoulHive §8➥ §7Deine §fSkyPvP §7Community",
        ""
    );

    @Override
    public void setup(JavaPlugin plugin) {
        super.runTaskTimer(plugin, 0L, PERIOD);
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            String footer = "§7Du befindest dich gerade ";
            World world = player.getWorld();

            if (Settings.SKYBLOCK_WORLDS.contains(world)) {
                footer += "in der §fSkyBlock §7Welt.";
            } else if (world.equals(Settings.WORLD_MAIN)) {
                int blockY = player.getLocation().getBlockY();

                if (blockY < Settings.SPAWN_HEIGHT) {
                    footer += "in der §fPvP Zone§7.";
                } else {
                    footer += "am §fSpawn§7.";
                }
            } else {
                footer += "in '§f" + world.getName() + "§7'.";
            }

            Tablist.send(
                player,
                String.join("\n", TAB_HEADER),
                String.join("\n", Arrays.asList("", footer, ""))
            );
        });
    }

}
