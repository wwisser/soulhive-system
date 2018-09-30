package de.skydust.system;

import de.skydust.system.motd.MotdService;
import de.skydust.system.service.ServiceManager;
import de.skydust.system.stats.service.StatsService;
import de.skydust.system.task.impl.TablistUpdateTask;
import de.skydust.system.task.service.TaskService;
import de.skydust.system.user.service.UserService;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class PluginLauncher extends JavaPlugin {

    public static final String PREFIX = "§9§lSkyDust §8➥ §7";
    public static final String NO_PERMISSION = "§cDu hast keinen Zugriff auf diesen Befehl.";
    public static final String COMMAND_USAGE = PREFIX + "Verwendung: §f";

    public static final String CONFIG_PATH = "plugins/SkyDust-System";

    public static final World WORLD_MAIN = Bukkit.getWorld("SkyDust");

    @Getter private static ServiceManager serviceManager;

    private TaskService taskService;
    private UserService userService;

    @Override
    public void onEnable() {
        this.taskService = new TaskService(this);
        this.userService = new UserService();

        this.taskService.registerTasks(new TablistUpdateTask());

        ServiceManager serviceManager = new ServiceManager(this);
        PluginLauncher.serviceManager = serviceManager;

        Arrays.asList(
            this.taskService,
            new MotdService(),
            this.userService,
            new StatsService()
        ).forEach(serviceManager::registerService);

        Bukkit.getOnlinePlayers().forEach(this.userService::loadUser);
    }

    @Override
    public void onDisable() {
        this.taskService.cancelTasks();

        Bukkit.getOnlinePlayers().forEach(this.userService::unloadUser);
    }

}
