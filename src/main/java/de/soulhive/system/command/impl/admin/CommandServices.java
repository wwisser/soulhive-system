package de.soulhive.system.command.impl.admin;

import de.soulhive.system.SoulHive;
import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.container.Container;
import de.soulhive.system.container.ContainerAction;
import de.soulhive.system.container.ContainerService;
import de.soulhive.system.service.Service;
import de.soulhive.system.service.ServiceManager;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.util.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class CommandServices extends CommandExecutorWrapper {

    private ContainerService containerService;

    public CommandServices() {
        ServiceManager serviceManager = SoulHive.getServiceManager();
        this.containerService = serviceManager.getService(ContainerService.class);

        Container container = new Container(
            "§0§lServices",
            new HashMap<ItemStack, ContainerAction>() {{
                for (Service service : serviceManager.getFeatureServices()) {
                    ItemStack itemStack = new ItemBuilder(Material.BOOK)
                        .name("§b" + service.getClass().getSimpleName())
                        .build();

                    this.put(itemStack, player -> {
                        player.sendMessage(service.getClass().getName());
                        player.closeInventory();
                    });
                }
            }}
        );
    }

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        ValidateCommand.permission(sender, Settings.PERMISSION_ADMIN);
        Player player = ValidateCommand.onlyPlayer(sender);


    }

}
