package de.soulhive.system.command.impl.none;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.setting.Settings;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;

/**
 * Placeholder class for unregistered commands.
 */
public class CommandNone extends CommandExecutorWrapper {

    @Override
    public void process(CommandSender sender, String[] args) throws CommandException {
        sender.sendMessage(Settings.PREFIX + "§cUnbekannter Befehl. Vertippt?");
    }

}
