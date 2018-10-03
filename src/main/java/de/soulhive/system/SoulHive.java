package de.soulhive.system;

import de.soulhive.system.command.CommandService;
import de.soulhive.system.listener.ListenerService;
import de.soulhive.system.motd.MotdService;
import de.soulhive.system.service.ServiceManager;
import de.soulhive.system.stats.service.StatsService;
import de.soulhive.system.task.impl.PlannedShutdownTask;
import de.soulhive.system.task.impl.TablistUpdateTask;
import de.soulhive.system.task.service.TaskService;
import de.soulhive.system.user.service.UserService;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class SoulHive extends JavaPlugin {

    @Getter private static ServiceManager serviceManager;

    @Override
    public void onEnable() {
        ServiceManager serviceManager = new ServiceManager(this);
        SoulHive.serviceManager = serviceManager;

        TaskService taskService = new TaskService(this);

        taskService.registerTasks(
            new TablistUpdateTask(),
            new PlannedShutdownTask()
        );

        Arrays.asList(
            taskService,
            new MotdService(),
            new UserService(),
            new StatsService(),
            new CommandService(),
            new ListenerService()
        ).forEach(serviceManager::registerService);
    }

    @Override
    public void onDisable() {
        SoulHive.serviceManager.getServices().forEach(SoulHive.serviceManager::unregisterService);
    }

}
