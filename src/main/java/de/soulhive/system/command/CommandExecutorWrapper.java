package de.soulhive.system.command;

import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.setting.Settings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class CommandExecutorWrapper implements CommandExecutor {

    @Override
    public final boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            this.process(sender, args);
        } catch (Exception e) {
            String message = e.getMessage();

            sender.sendMessage(e instanceof CommandException ? message : Settings.PREFIX + "§4Fehler: §c" + message);
        }

        return true;
    }

    public abstract void process(CommandSender sender, String[] args) throws CommandException;

}
