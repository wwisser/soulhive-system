package de.soulhive.system.command.impl.admin;

import com.google.common.collect.ImmutableMap;
import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.setting.Settings;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class CommandGamemode extends CommandExecutorWrapper {

    private static final String COMMAND_USAGE = "/gamemode <0|1|2|3> <Spieler";
    private static final Map<String, GameMode> GAME_MODES = ImmutableMap.of(
      "0", GameMode.SURVIVAL,
      "1", GameMode.CREATIVE,
      "2", GameMode.ADVENTURE,
      "3", GameMode.SPECTATOR
    );

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        ValidateCommand.permission(sender, "soulhive.gamemode");
        ValidateCommand.minArgs(1, args, COMMAND_USAGE);

        Player target;
        GameMode gameMode = GAME_MODES.getOrDefault(args[0], GameMode.SURVIVAL);

        if (args.length > 1) {
            target = ValidateCommand.target(args[1], sender);
            sender.sendMessage(
                Settings.PREFIX
                    + "Spielmodus von §f"
                    + target.getName()
                    + " §7zu §f"
                    + gameMode
                    + " §7geändert."
            );
        } else {
            target = ValidateCommand.onlyPlayer(sender);
        }

        target.sendMessage(Settings.PREFIX + "Dein Spielmodus wurde zu §f" + gameMode + " §7geändert.");
        target.setGameMode(gameMode);
    }

}
