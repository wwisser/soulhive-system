package de.soulhive.system.command.impl;

import de.soulhive.system.setting.Settings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandRawbc implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!commandSender.hasPermission(Settings.PERMISSION_ADMIN)) {
            commandSender.sendMessage(Settings.COMMAND_NO_PERMISSION);
            return true;
        }

        if (args.length < 1) {
            commandSender.sendMessage(Settings.COMMAND_USAGE + "/rawbc <Nachricht>");
            return true;
        }

        Bukkit.broadcastMessage(
            ChatColor.translateAlternateColorCodes('&', String.join(" ", args))
        );

        return true;
    }

}
