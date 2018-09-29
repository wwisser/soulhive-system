package de.skydust.system;

import de.skydust.system.motd.MotdService;
import de.skydust.system.service.ServiceManager;
import de.skydust.system.task.impl.TablistUpdateTask;
import de.skydust.system.task.service.TaskService;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginLauncher extends JavaPlugin {

    public static final String PREFIX = "§9§lSkyDust §8➥ §7";
    public static final String NO_PERMISSION = "§cDu hast keinen Zugriff auf diesen Befehl.";
    public static final String COMMAND_USAGE = PREFIX + "Verwendung: §f";

    public static final String CONFIG_PATH = "plugins/SkyDust-System";

    @Getter private static ServiceManager serviceManager;

    private TaskService taskService;

    @Override
    public void onEnable() {
        this.taskService = new TaskService(this);

        this.taskService.registerTasks(new TablistUpdateTask());

        PluginLauncher.serviceManager = new ServiceManager(this);
        PluginLauncher.serviceManager.registerServices(this.taskService, new MotdService());
    }

    @Override
    public void onDisable() {
        this.taskService.cancelTasks();
    }

}
