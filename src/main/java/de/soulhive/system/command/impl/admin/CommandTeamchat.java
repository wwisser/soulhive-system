package de.soulhive.system.command.impl.admin;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.setting.Settings;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class CommandTeamchat extends CommandExecutorWrapper {

    private static final String USAGE = "/teamchat <Nachricht>";

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        ValidateCommand.onlyPlayer(sender);
        ValidateCommand.permission(sender, Settings.PERMISSION_TEAM);
        ValidateCommand.minArgs(1, args, USAGE);

        Player player = (Player) sender;
        String message = String.join(" ", Arrays.copyOfRange(args, 0, args.length));

        Bukkit.getOnlinePlayers()
            .stream()
            .filter(players -> players.hasPermission(Settings.PERMISSION_TEAM))
            .forEach(
                receiver -> receiver.sendMessage(Settings.PREFIX + "§a" + player.getName() + "§7: §f" + message)
            );
    }

}