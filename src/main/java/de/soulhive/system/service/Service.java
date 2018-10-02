package de.soulhive.system.service;

import com.comphenix.protocol.events.PacketAdapter;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Service {

    default void initialize() {}

    default Set<Listener> getListeners() {
        return Collections.emptySet();
    }

    default Map<String, CommandExecutor> getCommands() {
        return Collections.emptyMap();
    }

    default Set<PacketAdapter> getPacketAdapters() {
        return Collections.emptySet();
    }

}
