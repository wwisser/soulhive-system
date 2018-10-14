package de.soulhive.system.stats;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import de.soulhive.system.SoulHive;
import de.soulhive.system.service.Service;
import de.soulhive.system.service.ServiceManager;
import de.soulhive.system.stats.commands.CommandStats;
import de.soulhive.system.stats.context.ToplistContext;
import de.soulhive.system.stats.context.impl.external.IslandLevelToplistContext;
import de.soulhive.system.stats.context.impl.internal.*;
import de.soulhive.system.stats.listeners.EntityDamageByEntityListener;
import de.soulhive.system.stats.listeners.PlayerDeathListener;
import de.soulhive.system.stats.tasks.PlaytimeUpdateTask;
import de.soulhive.system.stats.tasks.ToplistUpdateTask;
import de.soulhive.system.task.TaskService;
import de.soulhive.system.user.repository.UserRepository;
import de.soulhive.system.user.UserService;
import lombok.Getter;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.*;

@Getter
public class StatsService extends Service {

    private Map<Player, Player> lastHits = new HashMap<>();
    private UserService userService;
    private TaskService taskService;

    @Override
    public void initialize() {
        ServiceManager serviceManager = SoulHive.getServiceManager();

        this.userService = serviceManager.getService(UserService.class);
        this.taskService = serviceManager.getService(TaskService.class);

        this.taskService.registerTasks(new PlaytimeUpdateTask(this.userService));

        UserRepository userRepository = this.userService.getUserRepository();

        ToplistContext[] toplistContexts = {
            new KillToplistContext(userRepository),
            new DeathToplistContext(userRepository),
            new PlaytimeToplistContext(userRepository),
            new JewelToplistContext(userRepository),
            new VoteToplistContext(userRepository),
            new IslandLevelToplistContext(userRepository, ASkyBlockAPI.getInstance()),
        };

        this.taskService.registerTasks(new ToplistUpdateTask(toplistContexts));
    }

    @Override
    public Set<Listener> getListeners() {
        return new HashSet<Listener>() {{
            super.add(new EntityDamageByEntityListener(StatsService.this));
            super.add(new PlayerDeathListener(StatsService.this));
        }};
    }

    @Override
    public Map<String, CommandExecutor> getCommands() {
        return Collections.singletonMap("stats", new CommandStats(this.userService));
    }

}
