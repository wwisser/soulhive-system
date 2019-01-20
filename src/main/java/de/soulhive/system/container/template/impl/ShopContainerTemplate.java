package de.soulhive.system.container.template.impl;

import de.soulhive.system.container.Container;
import de.soulhive.system.container.ContainerService;
import de.soulhive.system.container.ContainerStorageLevel;
import de.soulhive.system.container.template.ContainerTemplate;
import de.soulhive.system.util.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ShopContainerTemplate extends ContainerTemplate {

    static final ItemStack ITEM_BACK = new ItemBuilder(Material.BARRIER)
        .name("§cZurück zur Shopübersicht")
        .build();

    private Container container;

    public ShopContainerTemplate(ContainerService containerService) {
        super(containerService);

        final Container.ContainerBuilder builder = new Container.ContainerBuilder("§0§lShop")
            .setStorageLevel(ContainerStorageLevel.STORED);

        final RankShopContainerTemplate rankShopTemplate = new RankShopContainerTemplate(
            super.containerService,
            this
        );
        final ScoreboardShopContainerTemplate scoreboardShopTemplate = new ScoreboardShopContainerTemplate(
            super.containerService,
            this
        );
        final PermissionShopContainerTemplate permissionShopTemplate = new PermissionShopContainerTemplate(
            super.containerService,
            this
        );

        builder.addAction(
            11,
            new ItemBuilder(Material.BOOK).name("§9§lRechte").build(),
            permissionShopTemplate::openContainer
        );
        builder.addAction(
            13,
            new ItemBuilder(Material.EMERALD).name("§9§lRänge").build(),
            rankShopTemplate::openContainer
        );
        builder.addAction(
            15,
            new ItemBuilder(Material.SIGN).name("§9§lScoreboards").build(),
            scoreboardShopTemplate::openContainer
        );

        final Container builtContainer = builder.build();

        this.container = builtContainer;
        super.containerService.registerContainer(builtContainer);
    }

    @Override
    protected void openContainer(Player player) {
        player.openInventory(this.container.getInventory());
    }

}
