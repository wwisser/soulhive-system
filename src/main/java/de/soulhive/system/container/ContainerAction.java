package de.soulhive.system.container;

import org.bukkit.entity.Player;

@FunctionalInterface
public interface ContainerAction {

    void process(Player player);

}
