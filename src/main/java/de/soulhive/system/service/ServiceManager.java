package de.soulhive.system.service;

import com.comphenix.protocol.ProtocolLibrary;
import de.soulhive.system.command.CommandService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class ServiceManager {

    private List<Service> services = new ArrayList<>();
    private final JavaPlugin plugin;

    public void registerService(Service service) {
        this.services.add(service);

        service.initialize();
        service.getListeners().forEach(listener ->
            this.plugin
                .getServer()
                .getPluginManager()
                .registerEvents(listener, this.plugin)
        );

        service.getCommands().forEach((name, commandExecutor) ->
            this.plugin.getCommand(name).setExecutor(commandExecutor)
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
    }

    public <T> T getService(Class<T> clazz) {
        for (Service service : this.services) {
            if (service.getClass() == clazz) {
                return clazz.cast(service);
            }
        }

        throw new NoSuchElementException("Could not find Service '" + clazz.getName() + "'");
    }

    public List<Service> getServices() {
        return new ArrayList<>(this.services);
    }

}
