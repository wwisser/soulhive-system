package de.soulhive.system.command.impl.essential;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandWorkbench extends CommandExecutorWrapper {

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        ValidateCommand.permission(sender,"soulhive.workbench");
        Player player = ValidateCommand.onlyPlayer(sender);

        player.openWorkbench(null, true);
    }

}