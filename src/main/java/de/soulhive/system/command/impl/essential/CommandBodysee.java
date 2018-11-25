package de.soulhive.system.command.impl.essential;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CommandBodysee extends CommandExecutorWrapper {

    private static final String USAGE = "/bodysee <target>";

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        ValidateCommand.permission(sender, "soulhive.bodysee");
        ValidateCommand.minArgs(1, args, USAGE);

        Player player = ValidateCommand.onlyPlayer(sender);
        Player target = ValidateCommand.target(args[0], player);

        Inventory inventory = Bukkit.createInventory(null, 9, "§0Rüstung von " + target.getName());

        for (int i = 0; i < target.getInventory().getArmorContents().length; i++) {
            inventory.setItem(i, target.getInventory().getArmorContents()[i]);
        }

        player.openInventory(inventory);
    }

}
