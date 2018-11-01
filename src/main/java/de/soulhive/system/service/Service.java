package de.soulhive.system.service;

import com.comphenix.protocol.events.PacketAdapter;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class Service {

    public void initialize() {}

    public void disable() {}

    public Set<Listener> getListeners() {
        return Collections.emptySet();
    }

    public Map<String, CommandExecutor> getCommands() {
        return Collections.emptyMap();
    }

    public Set<PacketAdapter> getPacketAdapters() {
        return Collections.emptySet();
    }

}
