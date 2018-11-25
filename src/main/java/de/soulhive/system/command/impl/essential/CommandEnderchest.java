package de.soulhive.system.command.impl.essential;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CommandEnderchest extends CommandExecutorWrapper {

    @Override
    public void process(CommandSender sender, String[] args) throws CommandException {
        ValidateCommand.permission(sender, "soulhive.enderchest");
        Player player = ValidateCommand.onlyPlayer(sender);

        Inventory enderchest;

        if (args.length > 0 && player.hasPermission("soulhive.enderchest.target")) {
            Player target = ValidateCommand.target(args[0]);
            enderchest = target.getEnderChest();
        } else {
            enderchest = player.getEnderChest();
        }

        player.openInventory(enderchest);
        player.playSound(player.getLocation(), Sound.CHEST_OPEN, 1, 1);
    }

}
