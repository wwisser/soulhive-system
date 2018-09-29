package de.skydust.system.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class ServiceManager {

    @Getter private List<Service> services = new ArrayList<>();
    private final JavaPlugin plugin;

    public void registerServices(Service... services) {
        for (Service service : services) {
            this.services.add(service);

            service.getListeners().forEach(listener ->
                this.plugin
                    .getServer()
                    .getPluginManager()
                    .registerEvents(listener, this.plugin)
            );

            service.getCommands().forEach((name, commandExecutor) ->
                this.plugin.getCommand(name).setExecutor(commandExecutor)
            );
        }
    }

    public <T> T getService(Class<T> clazz) {
        for (Service service : this.services) {
            if (service.getClass() == clazz) {
                return clazz.cast(service);
            }
        }

        throw new NoSuchElementException("Could not find Service '" + clazz.getName() + "'");
    }

}
