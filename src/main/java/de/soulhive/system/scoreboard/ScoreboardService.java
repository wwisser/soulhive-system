package de.soulhive.system.scoreboard;

import de.soulhive.system.SoulHive;
import de.soulhive.system.scoreboard.impl.AdminScoreboard;
import de.soulhive.system.scoreboard.impl.PvpScoreboard;
import de.soulhive.system.scoreboard.impl.UserScoreboard;
import de.soulhive.system.scoreboard.task.ScoreboardUpdateTask;
import de.soulhive.system.service.FeatureService;
import de.soulhive.system.service.Service;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.task.TaskService;
import de.soulhive.system.user.UserService;
import de.soulhive.system.util.Config;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@FeatureService
public class ScoreboardService extends Service {

    private Map<Player, DynamicScoreboard> scoreboards = new HashMap<>();
    private Config database = new Config(Settings.CONFIG_PATH, "scoreboards.yml");
    @Getter private UserService userService;

    @Override
    public void initialize() {
        this.userService = SoulHive.getServiceManager().getService(UserService.class);
        TaskService taskService = SoulHive.getServiceManager().getService(TaskService.class);

        taskService.registerTasks(new ScoreboardUpdateTask(this, this.userService));
    }

    @Override
    public void disable() {
        this.database.saveFile();
    }

    public DynamicScoreboard getScoreboard(Player player) {
        return this.scoreboards.get(player);
    }

    public void setScoreboard(Player player, DynamicScoreboard scoreboard) {
        this.scoreboards.put(player, scoreboard);
        scoreboard.show(player);
    }

    public void updateSelectedType(final Player player, final ScoreboardType type) {
        switch (type) {
            case USER:
                this.scoreboards.put(player, new UserScoreboard());
                break;
            case PVP:
                this.scoreboards.put(player, new PvpScoreboard());
                break;
            case ADMIN:
                this.scoreboards.put(player, new AdminScoreboard());
                break;
        }

        this.database.set(player.getUniqueId().toString(), type.ordinal());
    }

    public ScoreboardType fetchSelectedType(final Player player) {
        final String uuid = player.getUniqueId().toString();

        if (this.database.contains(uuid)) {
            return ScoreboardType.values()[this.database.getInt(uuid)];
        }

        return ScoreboardType.USER;
    }

}
