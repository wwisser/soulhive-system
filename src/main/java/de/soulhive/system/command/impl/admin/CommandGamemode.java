package de.soulhive.system.command.impl.admin;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.setting.Settings;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandGamemode extends CommandExecutorWrapper {

    private static final String COMMAND_USAGE = "/gamemode <0|1|2|3> <Spieler";

    @Override
    public void process(CommandSender sender, String[] args) throws CommandException {
        ValidateCommand.permission(sender, "soulhive.gamemode");
        ValidateCommand.minArgs(1, args, COMMAND_USAGE);

        Player target;
        GameMode gameMode = this.parseGamemode(args[0]);

        if (args.length > 1) {
            target = ValidateCommand.target(args[1]);
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

    private GameMode parseGamemode(String arg) {
        GameMode gameMode;

        switch (arg) {
            case "0":
                gameMode = GameMode.SURVIVAL;
                break;
            case "1":
                gameMode = GameMode.CREATIVE;
                break;
            case "2":
                gameMode = GameMode.ADVENTURE;
                break;
            case "3":
                gameMode = GameMode.SPECTATOR;
                break;
            default:
                gameMode = GameMode.SURVIVAL;
                break;
        }

        return gameMode;
    }

}
