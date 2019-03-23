package de.soulhive.system.container.template.impl;

import de.soulhive.system.SoulHive;
import de.soulhive.system.container.Container;
import de.soulhive.system.container.ContainerService;
import de.soulhive.system.container.ContainerStorageLevel;
import de.soulhive.system.container.action.ContainerAction;
import de.soulhive.system.container.action.impl.PurchaseContainerAction;
import de.soulhive.system.container.template.ContainerTemplate;
import de.soulhive.system.scoreboard.ScoreboardService;
import de.soulhive.system.scoreboard.ScoreboardType;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.util.PermissionUtils;
import de.soulhive.system.util.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ScoreboardShopContainerTemplate extends ContainerTemplate {

    private Map<ScoreboardType, ContainerAction> purchaseActions = new HashMap<>();
    private ShopContainerTemplate shopContainerTemplate;

    ScoreboardShopContainerTemplate(
        final ContainerService containerService,
        final ShopContainerTemplate shopContainerTemplate
    ) {
        super(containerService);
        this.shopContainerTemplate = shopContainerTemplate;
        final ScoreboardService scoreboardService = SoulHive.getServiceManager().getService(ScoreboardService.class);

        for (ScoreboardType type : ScoreboardType.values()) {
            this.purchaseActions.put(
                type,
                new PurchaseContainerAction(player -> {
                    PermissionUtils.addPermission(player.getName(), type.getPermission());
                    Bukkit.broadcastMessage(Settings.PREFIX + "§f" + player.getName() + " §7hat sich das Scoreboard " + type.getName() + " §7gekauft.");
                    Bukkit.broadcastMessage(Settings.PREFIX + "Jetzt auch mit Juwelen einkaufen §8§l=> §d§l/shop");
                    scoreboardService.updateSelectedType(player, type);
                }, type.getCosts())
            );
        }
    }

    @Override
    protected void openContainer(final Player player) {
        final Container.ContainerBuilder builder = new Container.ContainerBuilder("§0§lShop §0> §0§lScoreboards");

        builder.addAction(26, ShopContainerTemplate.ITEM_BACK, this.shopContainerTemplate::openContainer)
            .setStorageLevel(ContainerStorageLevel.NEW);

        int count = 12;
        for (ScoreboardType type : ScoreboardType.values()) {
            final boolean hasPermission = player.hasPermission(type.getPermission());

            final ItemStack item = new ItemBuilder(type.getMaterial()).name(type.getName()).modifyLore()
                .add("§r")
                .add("§7Eigenschaften:")
                .add(type.getProperties())
                .add("§r")
                .add(hasPermission ? "§aBereits gekauft" : "§7Preis: §d" + type.getCosts() + " Juwelen")
                .finish()
                .build();

            builder.addAction(
                count++,
                item,
                hasPermission
                    ? clicker -> clicker.playSound(clicker.getLocation(), Sound.CREEPER_HISS, 1, 1)
                    : this.purchaseActions.get(type)
            );
        }

        final Container container = builder.build();

        super.containerService.registerContainer(container);
        player.openInventory(container.getInventory());
    }

}
