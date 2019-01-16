package de.soulhive.system.container.template.impl;

import de.soulhive.system.container.Container;
import de.soulhive.system.container.ContainerService;
import de.soulhive.system.container.ContainerStorageLevel;
import de.soulhive.system.container.action.ContainerAction;
import de.soulhive.system.container.action.impl.PurchaseContainerAction;
import de.soulhive.system.container.template.ContainerTemplate;
import de.soulhive.system.rank.PremiumRank;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.util.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class RankShopContainerTemplate extends ContainerTemplate {

    private Map<String, ContainerAction> purchaseActions = new HashMap<>();

    public RankShopContainerTemplate(ContainerService containerService) {
        super(containerService);

        for (PremiumRank rank : PremiumRank.values()) {
            this.purchaseActions.put(
                rank.getGroupName(),
                new PurchaseContainerAction(player -> {
                    Bukkit.dispatchCommand(
                        Bukkit.getConsoleSender(),
                        "pex user " + player.getName() + " group set " + rank.getGroupName()
                    );
                    player.sendMessage(
                        Settings.PREFIX
                            + "Du hast dir den Rang "
                            + rank.getChatColor() + ChatColor.BOLD + rank.getName()
                            + " §7für §d"
                            + rank.getCosts()
                            + " Juwelen §7gekauft!"
                    );
                }, rank.getCosts())
            );
        }
    }

    @Override
    public void open(Player player) {
        final Container.ContainerBuilder builder = new Container.ContainerBuilder("§0§lShop")
            .setStorageLevel(ContainerStorageLevel.STORED);

        int count = 10;
        for (PremiumRank rank : PremiumRank.values()) {
            ContainerAction action = player.hasPermission(rank.getPermission())
                ? ContainerAction.NONE
                : this.purchaseActions.get(rank.getGroupName());
            ItemStack itemStack = new ItemBuilder(rank.getMaterial())
                .name("§7Rang " + rank.getChatColor() + "§l" + rank.getName())
                .modifyLore()
                .add("")
                .add(
                    player.hasPermission(rank.getPermission())
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
