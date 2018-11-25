package de.soulhive.system.command.impl.essential;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandEnchanter extends CommandExecutorWrapper {

    @Override
    public void process(CommandSender sender, String[] args) throws CommandException {
        ValidateCommand.permission(sender, "soulhive.enchanter");
        Player player = ValidateCommand.onlyPlayer(sender);

        player.openEnchanting(null, true);
    }

}
