package de.soulhive.system.scoreboard.task;

import de.soulhive.system.scoreboard.ScoreboardService;
import de.soulhive.system.scoreboard.impl.UserScoreboard;
import de.soulhive.system.task.ComplexTask;
import de.soulhive.system.user.User;
import de.soulhive.system.user.UserService;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

@AllArgsConstructor
public class ScoreboardUpdateTask extends BukkitRunnable implements ComplexTask {

    private static final long PERIOD = 10;

    private ScoreboardService scoreboardService;
    private UserService userService;

    @Override
    public void setup(JavaPlugin plugin) {
        super.runTaskTimer(plugin, 0L, PERIOD);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            User user = this.userService.getUser(player);
            UserScoreboard userScoreboard;

            if (this.scoreboardService.getScoreboard(player) != null) {
                userScoreboard = (UserScoreboard) this.scoreboardService.getScoreboard(player);
            } else {
                player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());

                userScoreboard = new UserScoreboard();
                this.scoreboardService.setScoreboard(player, userScoreboard);
            }

            userScoreboard.update(user);
            Scoreboard scoreboard = player.getScoreboard();

            if (scoreboard == null) {
                scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
                player.setScoreboard(scoreboard);
            }

            Objective objective = scoreboard.getObjective("userJewels");
            if (objective == null) {
                objective = scoreboard.registerNewObjective("userJewels", "dummy");
                objective.setDisplayName("§dJuwelen");
                objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
                player.setScoreboard(scoreboard);
            }

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                User onlineUser = this.userService.getUser(onlinePlayer);

                objective.getScore(onlinePlayer).setScore((int) onlineUser.getJewels());
            }
        }
    }

}
