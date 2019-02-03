package de.soulhive.system.command.impl.admin;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.setting.Settings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandKickall extends CommandExecutorWrapper {

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        ValidateCommand.permission(sender, Settings.PERMISSION_ADMIN);
        ValidateCommand.minArgs(1, args, "/kickall <message>");

        final String reason = ChatColor.translateAlternateColorCodes('&', args[0]);

        Bukkit.getOnlinePlayers()
            .stream()
            .filter(player -> player != sender)
            .forEach(player -> player.kickPlayer(Settings.PREFIX + "§7Du wurdest gekickt:\n" + reason));

        sender.sendMessage(Settings.PREFIX + "Du hast alle Spieler für '§f" + reason + "§7' gekickt.");
    }

}
