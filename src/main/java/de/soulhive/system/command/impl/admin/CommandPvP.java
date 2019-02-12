package de.soulhive.system.command.impl.admin;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.setting.Settings;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandPvP extends CommandExecutorWrapper {

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        ValidateCommand.permission(sender, Settings.PERMISSION_ADMIN);
        final Player player = ValidateCommand.onlyPlayer(sender);
        final World world = player.getWorld();

        world.setPVP(!world.getPVP());
        Bukkit.broadcastMessage(
            Settings.PREFIX + "PvP in der Welt '§f" + world.getName() + "§f' §7wurde "
                + (world.getPVP() ? "§aaktiviert" : "§cdeaktiviert") + "§7."
        );
    }

}
