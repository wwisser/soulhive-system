package de.skydust.system.service;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public interface Service {

    default Set<Listener> getListeners() {
        return Collections.emptySet();
    }

    default Map<String, CommandExecutor> getCommands() {
        return Collections.emptyMap();
    }

}
