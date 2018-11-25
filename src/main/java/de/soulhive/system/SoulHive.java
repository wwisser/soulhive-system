package de.soulhive.system;

import de.soulhive.system.command.CommandService;
import de.soulhive.system.delay.DelayService;
import de.soulhive.system.listener.ListenerService;
import de.soulhive.system.motd.MotdService;
import de.soulhive.system.service.ServiceManager;
import de.soulhive.system.stats.StatsService;
import de.soulhive.system.task.impl.PlannedShutdownTask;
import de.soulhive.system.task.impl.TablistUpdateTask;
import de.soulhive.system.task.TaskService;
import de.soulhive.system.thread.ShutdownHookThread;
import de.soulhive.system.user.UserService;
import de.soulhive.system.vanish.VanishService;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class SoulHive extends JavaPlugin {

    @Getter private static ServiceManager serviceManager;

    @Override
    public void onEnable() {
        ServiceManager serviceManager = new ServiceManager(this);
        SoulHive.serviceManager = serviceManager;

        PlannedShutdownTask plannedShutdownTask = new PlannedShutdownTask();
        TaskService taskService = new TaskService(this);

        taskService.registerTasks(
            new TablistUpdateTask(),
            plannedShutdownTask
        );

        Runtime.getRuntime().addShutdownHook(new ShutdownHookThread(plannedShutdownTask));

        Arrays.asList(
            taskService,
            new MotdService(),
            new UserService(),
            new DelayService(),
            new StatsService(),
            new CommandService(this),
            new ListenerService(),
            new VanishService(this)
        ).forEach(serviceManager::registerService);
    }

    @Override
    public void onDisable() {
        SoulHive.serviceManager.getServices().forEach(SoulHive.serviceManager::unregisterService);
    }

}
