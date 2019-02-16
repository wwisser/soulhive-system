package de.soulhive.system.command.impl.essential;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.setting.Settings;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandEnchanter extends CommandExecutorWrapper {

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        ValidateCommand.permission(sender, "soulhive.enchanter");
        Player player = ValidateCommand.onlyPlayer(sender);

        player.sendMessage(Settings.PREFIX + "§cDieser Befehl ist aktuell deaktiviert.");
        //player.openEnchanting(null, true);
    }

}
