package de.soulhive.system.command.impl.essential;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.setting.Settings;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandFly extends CommandExecutorWrapper {

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        ValidateCommand.permission(sender, "soulhive.fly");
        Player player = ValidateCommand.onlyPlayer(sender);
        boolean flying = player.isFlying();

        player.setAllowFlight(!flying);
        player.setFlying(!flying);
        player.sendMessage(
            Settings.PREFIX
                + "Dein Flugmodus wurde §f"
                + (flying ? "deaktiviert" : "aktiviert")
                + "§7."
        );

        if (!flying) {
            player.playSound(player.getLocation(), Sound.ENDERDRAGON_WINGS, 1, 1);
        }
    }

}
