package de.soulhive.system.stats.tasks;

import de.soulhive.system.SoulHive;
import de.soulhive.system.stats.context.ToplistContext;
import de.soulhive.system.stats.label.StatsLabel;
import de.soulhive.system.task.ComplexTask;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
public class ToplistUpdateTask extends BukkitRunnable implements ComplexTask {

    private static final long PERIOD = 20L * 60 * 5;
    private static final int POSITION_Z_START = -355;
    private static final int HEAD_AMOUNT = 5;
    private static final Location LABEL_LOCATION = new Location(SoulHive.WORLD_MAIN, -51, 186, -353);

    private final ToplistContext[] toplistContexts;
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
        Location currentHead = new Location(SoulHive.WORLD_MAIN, -50, 185, POSITION_Z_START);
        int count = 0;

        for (Map.Entry<String, ? super Number> entry : topList.entrySet()) {
            Skull skull = (Skull) currentHead.getBlock().getState();

            skull.setOwner(entry.getKey());
            skull.update();
            currentHead.getWorld().playSound(currentHead, Sound.CHICKEN_EGG_POP, 3, 5);

            Location lowerLoc = currentHead.clone().add(0, -1, 0);

            Sign sign = (Sign) lowerLoc.getBlock().getState();

            sign.setLine(0, "§l#" + (count + 1));
            sign.setLine(1, entry.getKey());
            sign.setLine(2, String.valueOf(entry.getValue()));
            sign.setLine(3, statsLabel.getAdditive());
            sign.update();

            currentHead.add(0, 0, 1);
            count++;
        }

        if (this.currentIndex >= (this.toplistContexts.length - 1)) {
            this.currentIndex = 0;
        } else {
            this.currentIndex++;
        }
    }

    private Map<String, ? super Number> sort(Map<String, ? super Number> data) {
        Map<String, ? super Number> topList = new LinkedHashMap<>();

        for (int i = 0; i < HEAD_AMOUNT; i++) {
            Number maxValue = 0;
            String maxValueKey = "";

            for (Map.Entry<String, ? super Number> entry : data.entrySet()) {
                if (((Number) entry.getValue()).longValue() > maxValue.longValue()) {
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
