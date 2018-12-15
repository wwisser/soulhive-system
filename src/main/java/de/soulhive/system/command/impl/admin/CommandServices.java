package de.soulhive.system.command.impl.admin;

import de.soulhive.system.SoulHive;
import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.container.Container;
import de.soulhive.system.container.ContainerService;
import de.soulhive.system.container.ContainerStorageLevel;
import de.soulhive.system.service.Service;
import de.soulhive.system.service.ServiceManager;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.util.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CommandServices extends CommandExecutorWrapper {

    private Inventory inventory;

    @Override
    public void initialize() {
        ServiceManager serviceManager = SoulHive.getServiceManager();
        ContainerService containerService = serviceManager.getService(ContainerService.class);

        Container.ContainerBuilder containerBuilder = new Container.ContainerBuilder("§0§lServices")
            .withSize(6 * 9)
            .withStorageLevel(ContainerStorageLevel.STORED);

        for (int i = 0; i < serviceManager.getServices().size(); i++) {
            Service service = serviceManager.getServices().get(i);

            containerBuilder.withAction(
                i,
                new ItemBuilder(Material.BOOK).name(service.getClass().getSimpleName()).build(),
                player -> {
                    player.closeInventory();
                    player.sendMessage(service.getClass().getName());
                }
            );
        }

        Container builtContainer = containerBuilder.build();

        containerService.registerContainer(builtContainer);
        this.inventory = builtContainer.getInventory();
    }

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        ValidateCommand.permission(sender, Settings.PERMISSION_ADMIN);
        Player player = ValidateCommand.onlyPlayer(sender);

        player.openInventory(this.inventory);
    }

}
