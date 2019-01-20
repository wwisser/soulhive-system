package de.soulhive.system.container.template.impl;

import de.soulhive.system.container.ContainerService;
import de.soulhive.system.container.template.ContainerTemplate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class PermissionShopContainerTemplate extends ContainerTemplate {

    private ShopContainerTemplate shopContainerTemplate;

    PermissionShopContainerTemplate(final ContainerService containerService, final ShopContainerTemplate shopTemplate) {
        super(containerService);

        this.shopContainerTemplate = shopTemplate;
    }

    // obsidian: fly (unique), hat, skull, trash, enchanter, bodysee
    // emerald: invsee (unique), enderchest, bottle, fill
    // diamond: stack (unique), workbench, cook
    // gold: repair, feed, heal, tpa, /tpahere

    // rechte zum kaufen: workbench (2k), enchanter(2k), enderchest(3k), repair (5k)

    @Override
    protected void openContainer(Player player) {

    }

    @AllArgsConstructor
    @Getter
    private enum ShopPermission {
        WORKBENCH(Material.WORKBENCH, 2000),
        ENCHANTER(Material.ENCHANTMENT_TABLE, 2000),
        ENDERCHEST(Material.ENDER_CHEST, 5000),
        REPAIR(Material.ANVIL, 5000);

        private Material material;
        private int costs;

        public String getPermission() {
            return "soulhive." + this.toString().toLowerCase();
        }

        public String getName() {
            return StringUtils.capitalize(this.toString().toLowerCase());
        }

    }

}
