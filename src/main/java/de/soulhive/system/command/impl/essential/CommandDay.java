package de.soulhive.system.command.impl.essential;

import de.skycade.system.setting.Message;
import de.skycade.system.setting.Setting;
import de.skycade.system.util.ActionBar;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandDay implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label,
                             String[] args) {
        if (commandSender.hasPermission("skycade.day")) {
            World world = commandSender instanceof Player ? ((Player) commandSender).getWorld() : Setting.WORLD_MAIN;
            world.setTime(0);
        } else {
            ActionBar.send(Message.NO_PERMISSION, (Player) commandSender);
        }
        return true;
    }

}