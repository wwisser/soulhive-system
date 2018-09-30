package de.skydust.system.stats.tasks;

import de.skydust.system.PluginLauncher;
import de.skydust.system.stats.context.ToplistContext;
import de.skydust.system.stats.label.StatsLabel;
import de.skydust.system.task.ComplexTask;
import lombok.AllArgsConstructor;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedHashMap;
import java.util.Map;

@AllArgsConstructor
public class ToplistUpdateTask extends BukkitRunnable implements ComplexTask {

    private static final long PERIOD = 20L * 60 * 5;
    private static final int POSITION_Z_START = 355;
    private static final int HEAD_AMOUNT = 5;
    private static final Location LABEL_LOCATION = new Location(PluginLauncher.WORLD_MAIN, -51, 186, -353);

    private ToplistContext[] toplistContexts;
    private int currentIndex = 0;

    @Override
    public void setup(JavaPlugin plugin) {
        super.runTaskTimer(plugin, 0L, PERIOD);
    }

    @Override
    public void run() {
        ToplistContext toplistContext = this.toplistContexts[this.currentIndex];

        StatsLabel statsLabel = toplistContext.getLabel();
        Sign labelSign = (Sign) LABEL_LOCATION.getBlock().getState();

        labelSign.setLine(2, "§l" + statsLabel.getDisplayName());
        labelSign.update();

        Map<String, ? super Number> topList = this.sort(toplistContext.getData());
        Location currentHead = new Location(PluginLauncher.WORLD_MAIN, -51, 185, POSITION_Z_START);
        int count = 0;

        for (Map.Entry<String, ? super Number> entry : topList.entrySet()) {
            Skull skull = (Skull) currentHead.getBlock().getState();

            skull.setOwner(entry.getKey());
            skull.update();

            Location lowerLoc = currentHead.clone().add(0, -1, 0);

            Sign sign = (Sign) lowerLoc.getBlock().getState();
            sign.setLine(0, "§l#" + (count + 1));
            sign.setLine(1, "--- * ---");
            sign.setLine(2, String.valueOf(entry.getValue()));
            sign.setLine(3, statsLabel.getAdditive());

            currentHead.add(0, 0, count);
            count++;
        }

        if (this.currentIndex < this.toplistContexts.length) {
            this.currentIndex++;
        } else {
            this.currentIndex = 0;
        }
    }

    private Map<String, ? super Number> sort(Map<String, ? super Number> data) {
        Map<String, ? super Number> topList = new LinkedHashMap<>();

        for (int i = 0; i < HEAD_AMOUNT; i++) {
            Number maxValue = 0;
            String maxValueKey = "";

            for (Map.Entry<String, ? super Number> entry : data.entrySet()) {
                if (((Number) entry).longValue() > maxValue.longValue()) {
                    maxValue = (Number) entry.getValue();
                    maxValueKey = entry.getKey();
                }
            }

            data.remove(maxValueKey);
            topList.put(maxValueKey, maxValue);
        }

        return topList;
    }

}
