package de.soulhive.system.scoreboard.task;

import de.soulhive.system.scoreboard.DynamicScoreboard;
import de.soulhive.system.scoreboard.ScoreboardService;
import de.soulhive.system.scoreboard.impl.AdminScoreboard;
import de.soulhive.system.scoreboard.impl.IslandScoreboard;
import de.soulhive.system.scoreboard.impl.PvpScoreboard;
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

            if (this.scoreboardService.getScoreboard(player) != null) {
                DynamicScoreboard dynScoreboard = this.scoreboardService.getScoreboard(player);

                switch (dynScoreboard.getType()) {
                    case USER:
                        ((UserScoreboard) dynScoreboard).update(user);
                        break;
                    case PVP:
                        ((PvpScoreboard) dynScoreboard).update(user);
                        break;
                    case ADMIN:
                        ((AdminScoreboard) dynScoreboard).update(player);
                        break;
                    case ISLAND:
                        ((IslandScoreboard) dynScoreboard).update(player);
                        break;
                }
            } else {
                player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());

                DynamicScoreboard dynScoreboard = null;

                switch (this.scoreboardService.fetchSelectedType(player)) {
                    case USER:
                        dynScoreboard = new UserScoreboard();
                        break;
                    case PVP:
                        dynScoreboard = new PvpScoreboard();
                        break;
                    case ADMIN:
                        dynScoreboard = new AdminScoreboard();
                        break;
                    case ISLAND:
                        dynScoreboard = new IslandScoreboard();
                        break;
                }

                this.scoreboardService.setScoreboard(player, dynScoreboard);
            }

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

                objective.getScore(onlinePlayer).setScore(onlineUser.getJewels());
            }
        }
    }

}
