package de.soulhive.system.container.template.impl;

import de.soulhive.system.container.Container;
import de.soulhive.system.container.ContainerService;
import de.soulhive.system.container.ContainerStorageLevel;
import de.soulhive.system.container.action.ContainerAction;
import de.soulhive.system.container.action.impl.PurchaseContainerAction;
import de.soulhive.system.container.template.ContainerTemplate;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.util.PermissionUtils;
import de.soulhive.system.util.item.ItemBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class PermissionShopContainerTemplate extends ContainerTemplate {

    private Map<ShopPermission, ContainerAction> purchaseActions = new HashMap<>();

    private ShopContainerTemplate shopContainerTemplate;

    PermissionShopContainerTemplate(final ContainerService containerService, final ShopContainerTemplate shopTemplate) {
        super(containerService);

        this.shopContainerTemplate = shopTemplate;

        for (ShopPermission shopPermission : ShopPermission.values()) {
            this.purchaseActions.put(
                shopPermission,
                new PurchaseContainerAction(
                    player -> {
                        PermissionUtils.addPermission(player.getName(), shopPermission.getPermission());
                        Bukkit.broadcastMessage(
                            Settings.PREFIX
                                + "§f" + player.getName()
                                + " §7hat sich Rechte für §f/" + shopPermission.getCommand() + " §7gekauft.");
                        Bukkit.broadcastMessage(Settings.PREFIX + "Jetzt auch mit Juwelen einkaufen §8§l=> §d§l/shop");
                    },
                    shopPermission.getCosts()
                )
            );
        }
    }

    @Override
    protected void openContainer(final Player player) {
        final Container.ContainerBuilder builder = new Container.ContainerBuilder("§0§lShop §0> §0§lRechte")
            .addAction(26, ShopContainerTemplate.ITEM_BACK, this.shopContainerTemplate::openContainer)
            .setStorageLevel(ContainerStorageLevel.STORED);

        int count = 10;
        for (ShopPermission shopPermission : ShopPermission.values()) {
            final boolean permission = player.hasPermission(shopPermission.getPermission());

            ContainerAction action = permission
                ? ContainerAction.NONE
                : this.purchaseActions.get(shopPermission);

            ItemStack itemStack = new ItemBuilder(shopPermission.getMaterial())
                .name("§9§l" + shopPermission.getName())
                .modifyLore()
                .add("")
                .add(
                    permission
                        ? "§aBereits gekauft"
                        : "§7Preis: §d" + shopPermission.getCosts() + " Juwelen"
                )
                .finish()
                .build();

            builder.addAction(count, itemStack, action);

            count += 2;
        }

        final Container builtContainer = builder.build();
        this.containerService.registerContainer(builtContainer);

        player.openInventory(builtContainer.getInventory());
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

        public String getCommand() {
            return this.toString().toLowerCase();
        }

        public String getPermission() {
            return "soulhive." + this.getCommand();
        }

        public String getName() {
            return StringUtils.capitalize(this.getCommand());
        }

    }

}
