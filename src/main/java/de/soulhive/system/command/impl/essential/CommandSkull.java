package de.soulhive.system.command.impl.essential;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.util.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandSkull extends CommandExecutorWrapper {

    private static final String COMMAND_USAGE = "/skull <name>";

    @Override
    public void process(CommandSender sender, String[] args) throws CommandException {
        ValidateCommand.permission(sender,"soulhive.skull");
        Player player = ValidateCommand.onlyPlayer(sender);

        ValidateCommand.minArgs(1, args, COMMAND_USAGE);

        ItemStack skullItem = new ItemBuilder(Material.SKULL_ITEM)
            .data((byte) 3)
            .name("§9Kopf von §f" + args[0])
            .owner(args[0])
            .build();

        player.getInventory().addItem(skullItem);
    }

}
