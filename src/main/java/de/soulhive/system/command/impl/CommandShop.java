package de.soulhive.system.command.impl;

import de.soulhive.system.SoulHive;
import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.impl.InvalidSenderException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.container.Container;
import de.soulhive.system.container.ContainerService;
import de.soulhive.system.container.ContainerStorageLevel;
import de.soulhive.system.container.action.ContainerAction;
import de.soulhive.system.container.action.impl.RankPurchaseContainerAction;
import de.soulhive.system.rank.PremiumRank;
import de.soulhive.system.util.item.ItemBuilder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class CommandShop extends CommandExecutorWrapper {

    private Map<String, ContainerAction> purchaseActions;
    private ContainerService containerService;

    @Override
    protected void initialize() {
        this.purchaseActions = new HashMap<>();
        this.containerService = SoulHive.getServiceManager().getService(ContainerService.class);

        for (PremiumRank rank : PremiumRank.values()) {
            this.purchaseActions.put(
                rank.getGroupName(),
                new RankPurchaseContainerAction(rank.getGroupName(), rank.getCosts())
            );
        }
    }

    @Override
    public void process(CommandSender sender, String label, String[] args) throws InvalidSenderException {
        final Player player = ValidateCommand.onlyPlayer(sender);

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
