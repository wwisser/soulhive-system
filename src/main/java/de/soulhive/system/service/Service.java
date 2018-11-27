package de.soulhive.system.service;

import com.comphenix.protocol.events.PacketAdapter;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;

import java.util.*;

public abstract class Service {

    private Set<Listener> listeners = new HashSet<>();
    private Set<PacketAdapter> packetAdapters = new HashSet<>();
    private Map<String, CommandExecutor> commands = new HashMap<>();

    public void initialize() {}

    public void disable() {}

    protected void registerListener(Listener listener) {
        this.listeners.add(listener);
    }

    protected void registerListeners(Listener... listeners) {
        this.listeners.addAll(Arrays.asList(listeners));
    }

    protected void registerPacketAdapter(PacketAdapter packetAdapter) {
        this.packetAdapters.add(packetAdapter);
    }

    protected void registerPacketAdapters(PacketAdapter... packetAdapters) {
        this.packetAdapters.addAll(Arrays.asList(packetAdapters));
    }

    protected void registerCommand(String name, CommandExecutor commandExecutor) {
        this.commands.put(name, commandExecutor);
    }

    final Set<Listener> getListeners() {
        return this.listeners;
    }

    final Set<PacketAdapter> getPacketAdapters() {
        return this.packetAdapters;
    }

    final Map<String, CommandExecutor> getCommands() {
        return this.commands;
    }

}
