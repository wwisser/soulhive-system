package de.soulhive.system.service;

import com.comphenix.protocol.ProtocolLibrary;
import de.soulhive.system.command.CommandService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Getter
public class ServiceManager {

    private List<Service> services = new ArrayList<>();
    private List<Service> featureServices = new ArrayList<>();
    private final JavaPlugin plugin;

    public void registerService(Service service) {
        this.services.add(service);

        if (service.getClass().getDeclaredAnnotation(FeatureService.class) != null) {
            this.featureServices.add(service);
        }

        service.initialize();
        service.getListeners().forEach(listener ->
            this.plugin
                .getServer()
                .getPluginManager()
                .registerEvents(listener, this.plugin)
        );

        service.getCommands().forEach((name, commandExecutor) -> {
                this.plugin.getCommand(name).setExecutor(commandExecutor);

                if (commandExecutor instanceof TabCompleter) {
                    this.plugin.getCommand(name).setTabCompleter((TabCompleter) commandExecutor);
                }
            }
        );

        service.getPacketAdapters().forEach(ProtocolLibrary.getProtocolManager()::addPacketListener);
    }

    public void unregisterService(Service service) {
        service.getCommands().forEach((name, commandExecutor) ->
            this.plugin.getCommand(name).setExecutor(CommandService.NO_COMMAND)
        );

        service.getListeners().forEach(HandlerList::unregisterAll);
        service.getPacketAdapters().forEach(ProtocolLibrary.getProtocolManager()::addPacketListener);
        service.disable();
        this.services.remove(service);
        this.featureServices.remove(service);
    }

    public <T> T getService(Class<T> clazz) {
        for (Service service : this.services) {
            if (service.getClass() == clazz) {
                return clazz.cast(service);
            }
        }

        throw new NoSuchElementException("Could not find service '" + clazz.getName() + "'");
    }

    public List<Service> getServices() {
        return new ArrayList<>(this.services);
    }

    public List<Service> getFeatures() {
        return Collections.unmodifiableList(this.featureServices);
    }

}
