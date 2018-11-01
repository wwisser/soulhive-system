package de.soulhive.system.command.impl.admin;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.setting.Settings;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class CommandChatclear extends CommandExecutorWrapper {

    @Override
    public void process(CommandSender sender, String[] args) throws CommandException {
        ValidateCommand.permission(sender, Settings.PERMISSION_TEAM);

        for (int i = 0; i < 300; i++) {
            String message = "";

            if (i <= 150) {
                message = "§0" + String.valueOf(Math.random());
            }

            final String finalMessage = message;
            Bukkit.getOnlinePlayers()
                .stream()
                .filter(player -> player.hasPermission(Settings.PERMISSION_TEAM))
                .forEach(player -> player.sendMessage(finalMessage));
        }

        Bukkit.broadcastMessage(Settings.PREFIX + "Der Chat wurde von §f" + sender.getName() + " §7geleert");
    }

}