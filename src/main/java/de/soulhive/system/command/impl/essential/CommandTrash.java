package de.soulhive.system.command.impl.essential;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CommandTrash extends CommandExecutorWrapper {

    private static final String INVENTORY_NAME = "Abfall";

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        ValidateCommand.permission(sender, "soulhive.trash");
        Player player = ValidateCommand.onlyPlayer(sender);
        Inventory inventory = Bukkit.createInventory(null, 6 * 9, INVENTORY_NAME);

        player.openInventory(inventory);
    }

}
