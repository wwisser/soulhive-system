package de.soulhive.system.scoreboard;

import de.soulhive.system.SoulHive;
import de.soulhive.system.scoreboard.task.ScoreboardUpdateTask;
import de.soulhive.system.service.FeatureService;
import de.soulhive.system.service.Service;
import de.soulhive.system.task.TaskService;
import de.soulhive.system.user.UserService;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@FeatureService
public class ScoreboardService extends Service {

    private Map<Player, DynamicScoreboard> scoreboards = new HashMap<>();
    @Getter private UserService userService;

    @Override
    public void initialize() {
        this.userService = SoulHive.getServiceManager().getService(UserService.class);
        TaskService taskService = SoulHive.getServiceManager().getService(TaskService.class);

        taskService.registerTasks(new ScoreboardUpdateTask(this, this.userService));
    }

    public DynamicScoreboard getScoreboard(Player player) {
        return this.scoreboards.get(player);
    }

    public void setScoreboard(Player player, DynamicScoreboard scoreboard) {
        this.scoreboards.put(player, scoreboard);
        scoreboard.show(player);
    }

}
