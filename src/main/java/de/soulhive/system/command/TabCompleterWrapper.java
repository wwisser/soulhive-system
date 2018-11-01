package de.soulhive.system.command;

import de.soulhive.system.command.exception.CommandException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Collections;
import java.util.List;

public abstract class TabCompleterWrapper implements TabCompleter {

    @Override
    public final List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        try {
            return this.processTabComplete(sender, args);
        } catch (CommandException e) {
            return Collections.emptyList();
        }
    }

    public abstract List<String> processTabComplete(CommandSender sender, String[] args) throws CommandException;

}
