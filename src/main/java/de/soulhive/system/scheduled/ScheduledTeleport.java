package de.soulhive.system.scheduled;

import de.soulhive.system.SoulHive;
import de.soulhive.system.task.ComplexTask;
import de.soulhive.system.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.function.BiConsumer;

@RequiredArgsConstructor
public class ScheduledTeleport {

    private static final int SECOND_IN_TICKS = 20;

    private TaskService taskService = SoulHive.getServiceManager().getService(TaskService.class);
    private final Location targetLocation;
    private final int seconds;
    private final BiConsumer<Player, Boolean> result;

    public void process(Player player) {
        ScheduledTeleportTask teleportTask = new ScheduledTeleportTask(player, this.result);

        this.taskService.registerTasks(teleportTask);
    }

    @RequiredArgsConstructor
    private class ScheduledTeleportTask extends BukkitRunnable implements ComplexTask {

        private final Player player;
        private final BiConsumer<Player, Boolean> result;
        private int count = 0;

        private int startX;
        private int startY;
        private int startZ;

        @Override
        public void setup(JavaPlugin plugin) {
            super.runTaskTimer(plugin, 0, 1);

            Location currentLocation = this.player.getLocation();
            this.startX = currentLocation.getBlockX();
            this.startY = currentLocation.getBlockY();
            this.startZ = currentLocation.getBlockZ();
        }

        @Override
        public void run() {
            Location currentLocation = this.player.getLocation();
            int currentX = currentLocation.getBlockX();
            int currentY = currentLocation.getBlockY();
            int currentZ = currentLocation.getBlockZ();

            if (currentX != this.startX || currentY != this.startY || currentZ != this.startZ) {
                this.result.accept(this.player, false);
                super.cancel();
                return;
            }

            if (this.count == (SECOND_IN_TICKS * ScheduledTeleport.this.seconds)) {
                this.result.accept(this.player, true);
                this.player.teleport(ScheduledTeleport.this.targetLocation);
                super.cancel();
                return;
            }

            this.count++;
        }
    }

}
