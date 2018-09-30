package de.skydust.system.task.service;

import de.skydust.system.service.Service;
import de.skydust.system.task.ComplexTask;
import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class TaskService implements Service {

    private final JavaPlugin plugin;
    private List<BukkitRunnable> tasks = new ArrayList<>();

    public void registerTasks(BukkitRunnable... bukkitRunnables) {
        for (BukkitRunnable bukkitRunnable : bukkitRunnables) {
            this.tasks.add(bukkitRunnable);

            if (bukkitRunnable instanceof ComplexTask) {
                ((ComplexTask) bukkitRunnable).setup(this.plugin);
            } else {
                bukkitRunnable.runTask(this.plugin);
            }
        }
    }

    public void cancelTasks() {
        this.tasks.forEach(BukkitRunnable::cancel);
    }

}
