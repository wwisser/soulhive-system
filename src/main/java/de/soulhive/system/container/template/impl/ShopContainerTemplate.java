package de.soulhive.system.container.template.impl;

import de.soulhive.system.container.Container;
import de.soulhive.system.container.ContainerService;
import de.soulhive.system.container.ContainerStorageLevel;
import de.soulhive.system.container.template.ContainerTemplate;
import org.bukkit.entity.Player;

public class ShopContainerTemplate extends ContainerTemplate {

    private Container container;

    public ShopContainerTemplate(ContainerService containerService) {
        super(containerService);

        final Container.ContainerBuilder builder = new Container.ContainerBuilder("§0§lShop")
            .setStorageLevel(ContainerStorageLevel.STORED);

        //TODO: add shop template links

        final Container builtContainer = builder.build();

        this.container = builtContainer;
        super.containerService.registerContainer(builtContainer);
    }

    @Override
    public void open(Player player) {
        player.openInventory(this.container.getInventory());
    }

}
