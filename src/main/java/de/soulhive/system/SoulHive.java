package de.soulhive.system;

import de.soulhive.system.combat.CombatService;
import de.soulhive.system.command.CommandService;
import de.soulhive.system.container.ContainerService;
import de.soulhive.system.delay.DelayService;
import de.soulhive.system.listener.ListenerService;
import de.soulhive.system.motd.MotdService;
import de.soulhive.system.npc.NpcService;
import de.soulhive.system.scoreboard.ScoreboardService;
import de.soulhive.system.service.Service;
import de.soulhive.system.service.ServiceManager;
import de.soulhive.system.stats.StatsService;
import de.soulhive.system.supply.SupplyService;
import de.soulhive.system.task.impl.PlannedShutdownTask;
import de.soulhive.system.task.impl.PlayerVoidKillTask;
import de.soulhive.system.task.impl.TablistUpdateTask;
import de.soulhive.system.task.TaskService;
import de.soulhive.system.thread.ShutdownHookThread;
import de.soulhive.system.user.UserService;
import de.soulhive.system.util.ReflectUtils;
import de.soulhive.system.vanish.VanishService;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class SoulHive extends JavaPlugin {

    @Getter private static JavaPlugin plugin;
    @Getter private static ServiceManager serviceManager;

    @Override
    public void onEnable() {
        SoulHive.plugin = this;
        ServiceManager serviceManager = new ServiceManager(this);
        SoulHive.serviceManager = serviceManager;

        PlannedShutdownTask plannedShutdownTask = new PlannedShutdownTask();
        TaskService taskService = new TaskService(this);

        taskService.registerTasks(
            new TablistUpdateTask(),
            plannedShutdownTask,
            new PlayerVoidKillTask()
        );

        Runtime.getRuntime().addShutdownHook(new ShutdownHookThread(plannedShutdownTask));

        Arrays.asList(
            taskService,
            new ContainerService(),
            new MotdService(),
            new UserService(),
            new DelayService(),
            new StatsService(),
            new CommandService(this),
            new ListenerService(),
            new VanishService(this),
            new CombatService(),
            new ScoreboardService(),
            new SupplyService(),
            new NpcService(this)
        ).forEach(serviceManager::registerService);

        ReflectUtils.getPacketObjects("de.soulhive.system.service.micro", Service.class)
            .forEach(serviceManager::registerService);
    }

    @Override
    public void onDisable() {
        SoulHive.serviceManager.getServices().forEach(SoulHive.serviceManager::unregisterService);
    }

}
