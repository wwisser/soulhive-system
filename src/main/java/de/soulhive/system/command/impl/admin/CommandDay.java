package de.soulhive.system.command.impl.admin;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.setting.Settings;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandDay extends CommandExecutorWrapper {

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        ValidateCommand.permission(sender, Settings.PERMISSION_TEAM);

        World world = sender instanceof Player ? ((Player) sender).getWorld() : Settings.WORLD_MAIN;
        world.setTime(0);

        sender.sendMessage(Settings.PREFIX + "Die Zeit in '§f" + world.getName() + "§7' wurde auf §f0 §7gesetzt.");
    }

}
