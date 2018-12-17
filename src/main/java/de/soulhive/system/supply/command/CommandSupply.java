package de.soulhive.system.supply.command;

import de.soulhive.system.SoulHive;
import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.container.Container;
import de.soulhive.system.container.ContainerService;
import de.soulhive.system.container.ContainerStorageLevel;
import de.soulhive.system.service.ServiceManager;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.supply.SupplyService;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;


public class CommandSupply extends CommandExecutorWrapper {

    private ContainerService containerService;
    private SupplyService supplyService;

    @Override
    public void initialize() {
        final ServiceManager serviceManager = SoulHive.getServiceManager();

        this.containerService = serviceManager.getService(ContainerService.class);
        this.supplyService = serviceManager.getService(SupplyService.class);
    }

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        ValidateCommand.permission(sender, Settings.PERMISSION_ADMIN);
        final Player player = ValidateCommand.onlyPlayer(sender);

        final Container.ContainerBuilder builder = new Container.ContainerBuilder(
            "§0§lSupply Items §0| "
                + this.supplyService.getItemStacks().size()
                + "/" + this.supplyService.getSignLocations().size()
        ).setSize(6 * 9)
            .setStorageLevel(ContainerStorageLevel.NEW)
            .setEventCancelled(false)
            .setInventoryCloseHook((closer, inventoryCloseEvent) ->
                this.supplyService.setItems(
                    Arrays.stream(inventoryCloseEvent.getInventory().getContents())
                        .filter(Objects::nonNull)
                        .filter(itemStack -> itemStack.getType() != Material.AIR)
                        .collect(Collectors.toList())
                )
            );

        int count = 0;
        for (ItemStack itemStack : this.supplyService.getItemStacks()) {
            builder.addItem(count++, itemStack);
        }

        final Container builtContainer = builder.build();

        this.containerService.registerContainer(builtContainer);
        player.openInventory(builtContainer.getInventory());
    }

}
