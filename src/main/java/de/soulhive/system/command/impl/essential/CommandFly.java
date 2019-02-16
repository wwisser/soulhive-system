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
        boolean allowed = player.getAllowFlight();

        if (!player.hasPermission(Settings.PERMISSION_TEAM)
            && player.getWorld().equals(Settings.WORLD_MAIN)
            && player.getLocation().getBlockY() < Settings.SPAWN_HEIGHT) {
            player.sendMessage(Settings.PREFIX + "§cDu darfst im PvP-Bereich kein Fly verwenden.");
            player.setFlying(false);
            player.setAllowFlight(false);
            return;
        }

        player.setAllowFlight(!allowed);
        player.setFlying(!allowed);
        player.sendMessage(
            Settings.PREFIX
                + "Dein Flugmodus wurde §f"
                + (allowed ? "deaktiviert" : "aktiviert")
                + "§7."
        );

        if (!allowed) {
            player.playSound(player.getLocation(), Sound.ENDERDRAGON_WINGS, 1, 1);
        }
    }

}
