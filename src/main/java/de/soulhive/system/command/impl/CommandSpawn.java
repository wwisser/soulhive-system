package de.soulhive.system.command.impl;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.scheduled.AlreadyInTeleportException;
import de.soulhive.system.scheduled.ScheduledTeleport;
import de.soulhive.system.setting.Settings;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.function.BiConsumer;

public class CommandSpawn extends CommandExecutorWrapper {

    private static final String MESSAGE_SUCCESS = Settings.PREFIX + "Du wurdest zum §fSpawn §7teleportiert.";
    private static final Location TARGET_LOCATION = Settings.LOCATION_SPAWN;
    private static final int SECONDS = 3;
    private static final BiConsumer<Player, Boolean> RESULT = (player, success) -> {
        if (success) {
            player.sendMessage(MESSAGE_SUCCESS);
            player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
        } else {
            player.sendMessage(Settings.PREFIX + "§cDu hast dich bewegt - Teleportation abgebrochen.");
        }
    };
    private static final ScheduledTeleport SCHEDULED_TELEPORT = new ScheduledTeleport(TARGET_LOCATION, SECONDS, RESULT);

    @Override
    public void process(CommandSender sender, String[] args) throws CommandException {
        Player player = ValidateCommand.onlyPlayer(sender);

        if (player.hasPermission(Settings.PERMISSION_ADMIN)) {
            player.teleport(TARGET_LOCATION);
            player.sendMessage(MESSAGE_SUCCESS);
        } else {
            try {
                SCHEDULED_TELEPORT.process(player);
                player.sendMessage(
                    Settings.PREFIX + "Du wirst in §f" + SECONDS + " §7Sekunden teleportiert. Bewege dich nicht."
                );
            } catch (AlreadyInTeleportException e) {
                player.sendMessage(Settings.PREFIX + "§cDu wirst bereits teleportiert.");
            }
        }
    }

}
