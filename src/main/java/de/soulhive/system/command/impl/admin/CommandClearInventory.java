package de.soulhive.system.command.impl.admin;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.setting.Settings;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandClearInventory extends CommandExecutorWrapper {

    @Override
    public void process(CommandSender sender, String[] args) throws CommandException {
        ValidateCommand.permission(sender, Settings.PERMISSION_ADMIN);
        Player player = ValidateCommand.onlyPlayer(sender);

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.playSound(player.getLocation(), Sound.NOTE_STICKS, 1, 1);
        player.sendMessage(Settings.PREFIX + "Dein Inventar wurde §fgeleert§7!");
    }

}
