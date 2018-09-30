package de.skydust.system.stats.tasks;

import de.skydust.system.task.ComplexTask;
import de.skydust.system.user.User;
import de.skydust.system.user.service.UserService;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

@AllArgsConstructor
public class PlaytimeUpdateTask extends BukkitRunnable implements ComplexTask {

    private static final long PERIOD_MINUTE = 20L * 60;

    private UserService userService;

    @Override
    public void setup(JavaPlugin plugin) {
        super.runTaskTimer(plugin, PERIOD_MINUTE, PERIOD_MINUTE);
    }

    @Override
    public void run() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            User user = this.userService.getUser(onlinePlayer);

            user.setPlaytime(user.getPlaytime() + 1);
        }
    }

}
