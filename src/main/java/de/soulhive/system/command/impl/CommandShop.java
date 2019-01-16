package de.soulhive.system.command.impl;

import de.soulhive.system.SoulHive;
import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.impl.InvalidSenderException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.container.ContainerService;
import de.soulhive.system.container.template.impl.ShopContainerTemplate;
import org.bukkit.command.CommandSender;

public class CommandShop extends CommandExecutorWrapper {

    private ShopContainerTemplate shopContainerTemplate;

    @Override
    protected void initialize() {
        ContainerService containerService = SoulHive.getServiceManager().getService(ContainerService.class);
        this.shopContainerTemplate = new ShopContainerTemplate(containerService);
    }

    @Override
    public void process(CommandSender sender, String label, String[] args) throws InvalidSenderException {
        this.shopContainerTemplate.open(ValidateCommand.onlyPlayer(sender));
    }

}
