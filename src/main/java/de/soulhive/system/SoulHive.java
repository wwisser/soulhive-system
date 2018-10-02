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
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;

public class SoulHive extends JavaPlugin {

    public static final String PREFIX = "§9§lSoulHive §8➥ §7";
    public static final String NO_PERMISSION = "§cDu hast keinen Zugriff auf diesen Befehl.";
    public static final String COMMAND_USAGE = PREFIX + "Verwendung: §f";

    public static final String CONFIG_PATH = "plugins/SoulHive-System";

    public static final World WORLD_MAIN = Bukkit.getWorld("SoulHive");

    @Getter private static ServiceManager serviceManager;

    private TaskService taskService;
    private UserService userService;

    @Override
    public void onEnable() {
        this.taskService = new TaskService(this);
        this.userService = new UserService();

        this.taskService.registerTasks(new TablistUpdateTask(), new PlannedShutdownTask());

        ServiceManager serviceManager = new ServiceManager(this);
        SoulHive.serviceManager = serviceManager;

        Arrays.asList(
            this.taskService,
            new MotdService(),
            this.userService,
            new StatsService(),
            new CommandService(),
            new ListenerService()
        ).forEach(serviceManager::registerService);

        Bukkit.getOnlinePlayers().forEach(this.userService::loadUser);
    }

    @Override
    public void onDisable() {
        this.taskService.cancelTasks();

        Bukkit.getOnlinePlayers().forEach(this.userService::unloadUser);

        new ArrayList<>(SoulHive.serviceManager.getServices()).forEach(SoulHive.serviceManager::unregisterService);
    }

}
