package de.soulhive.system.container.template.impl;

import de.soulhive.system.container.Container;
import de.soulhive.system.container.ContainerService;
import de.soulhive.system.container.ContainerStorageLevel;
import de.soulhive.system.container.action.ContainerAction;
import de.soulhive.system.container.action.impl.PurchaseContainerAction;
import de.soulhive.system.container.template.ContainerTemplate;
import de.soulhive.system.rank.PremiumRank;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.util.PermissionUtils;
import de.soulhive.system.util.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class RankShopContainerTemplate extends ContainerTemplate {

    private Map<PremiumRank, ContainerAction> purchaseActions = new HashMap<>();
    private ShopContainerTemplate shopContainerTemplate;

    RankShopContainerTemplate(final ContainerService containerService, final ShopContainerTemplate shopTemplate) {
        super(containerService);

        this.shopContainerTemplate = shopTemplate;
        for (PremiumRank rank : PremiumRank.values()) {
            this.purchaseActions.put(
                rank,
                new PurchaseContainerAction(player -> {
                    PermissionUtils.setRank(player.getName(), rank.getGroupName());
                    Bukkit.broadcastMessage(
                        Settings.PREFIX
                            + "§f" + player.getName()
                            + " §7hat sich den Rang " + rank.getChatColor() + ChatColor.BOLD + rank.getName() + " §7gekauft.");
                    Bukkit.broadcastMessage(Settings.PREFIX + "Jetzt auch mit Juwelen einkaufen §8§l=> §d§l/shop");
                }, rank.getCosts())
            );
        }
    }

    @Override
    protected void openContainer(Player player) {
        final Container.ContainerBuilder builder = new Container.ContainerBuilder("§0§lShop §0> §0§lRänge")
            .addAction(26, ShopContainerTemplate.ITEM_BACK, this.shopContainerTemplate::openContainer)
            .setStorageLevel(ContainerStorageLevel.STORED);

        int count = 10;
        for (PremiumRank rank : PremiumRank.values()) {
            final boolean permission = player.hasPermission(rank.getPermission());

            ContainerAction action = permission
                ? ContainerAction.NONE
                : this.purchaseActions.get(rank);

            ItemStack itemStack = new ItemBuilder(rank.getMaterial())
                .name("§7Rang " + rank.getChatColor() + "§l" + rank.getName())
                .modifyLore()
                .add("")
                .add(PremiumRank.PERMISSIONS_DEFAULT)
                .add(rank.getPermissions())
                .add("")
                .add(
                    permission
                        ? "§aBereits gekauft"
                        : "§7Preis: §d" + rank.getCosts() + " Juwelen"
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

}
