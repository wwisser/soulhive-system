package de.soulhive.system.command.impl.essential;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandInvsee extends CommandExecutorWrapper {

    private static final String USAGE = "/bodysee <target>";

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        ValidateCommand.permission(sender, "soulhive.invsee");
        ValidateCommand.minArgs(1, args, USAGE);

        Player player = ValidateCommand.onlyPlayer(sender);
        Player target = ValidateCommand.target(args[0], player);

        player.openInventory(target.getInventory());
    }

}