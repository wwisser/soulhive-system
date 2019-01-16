package de.soulhive.system.container;

import de.soulhive.system.container.ContainerService;
import org.bukkit.entity.Player;

public interface ContainerTemplate {

    void open(Player player, ContainerService containerService);

}
