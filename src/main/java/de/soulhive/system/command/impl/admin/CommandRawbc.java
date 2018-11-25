package de.soulhive.system.command.impl.admin;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.setting.Settings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandRawbc extends CommandExecutorWrapper {

    private static final String USAGE = "/rawbc <message>";

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        ValidateCommand.permission(sender, Settings.PERMISSION_ADMIN);
        ValidateCommand.minArgs(1, args, USAGE);

        Bukkit.broadcastMessage(
            ChatColor.translateAlternateColorCodes('&', String.join(" ", args))
        );
    }

}
