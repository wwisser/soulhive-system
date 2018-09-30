package de.skydust.system.stats.service;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import de.skydust.system.PluginLauncher;
import de.skydust.system.service.Service;
import de.skydust.system.service.ServiceManager;
import de.skydust.system.stats.context.ToplistContext;
import de.skydust.system.stats.context.impl.external.IslandLevelToplistContext;
import de.skydust.system.stats.context.impl.internal.*;
import de.skydust.system.stats.listeners.EntityDamageByEntityListener;
import de.skydust.system.stats.listeners.PlayerDeathListener;
import de.skydust.system.stats.tasks.PlaytimeUpdateTask;
import de.skydust.system.stats.tasks.ToplistUpdateTask;
import de.skydust.system.task.service.TaskService;
import de.skydust.system.user.repository.UserRepository;
import de.skydust.system.user.service.UserService;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.*;

@Getter
public class StatsService implements Service {

    private Map<Player, Player> lastHits = new HashMap<>();
    private UserService userService;
    private TaskService taskService;

    @Override
    public void initialize() {
        ServiceManager serviceManager = PluginLauncher.getServiceManager();

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

}
