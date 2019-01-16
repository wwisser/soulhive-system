package de.soulhive.system.container.template;

import de.soulhive.system.container.ContainerService;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;

@AllArgsConstructor
public abstract class ContainerTemplate {

    protected ContainerService containerService;

    public abstract void open(Player player);

}
