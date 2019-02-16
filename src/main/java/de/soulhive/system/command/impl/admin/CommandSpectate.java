package de.soulhive.system.command.impl.admin;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.setting.Settings;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSpectate extends CommandExecutorWrapper {

    private static final String USAGE = "/spectate <target>";

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        ValidateCommand.permission(sender, "soulhive.spectate");
        ValidateCommand.minArgs(1, args, USAGE);

        Player player = ValidateCommand.onlyPlayer(sender);

        if (args[0].equalsIgnoreCase("-u")) {
            player.teleport(Settings.LOCATION_SPAWN);
            player.setGameMode(GameMode.SURVIVAL);
            player.chat("/vanish");
            return;
        }

        Player target = ValidateCommand.target(args[0], player);

        player.chat("/vanish");
        player.chat("/gamemode 3");
        player.chat("/tp " + target.getName());
    }

}
