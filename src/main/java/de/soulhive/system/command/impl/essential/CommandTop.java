package de.soulhive.system.command.impl.essential;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.util.nms.ActionBar;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTop extends CommandExecutorWrapper {

    @Override
    public void process(CommandSender sender, String[] args) throws CommandException {
        ValidateCommand.permission(sender, "soulhive.top");
        Player player = ValidateCommand.onlyPlayer(sender);


        Block block = player.getWorld().getHighestBlockAt(player.getLocation());

        if (block == null || (block.getLocation().getBlockY() < player.getLocation().getBlockY())) {
            ActionBar.send("§cDu bist bereits an der höchsten Stelle.", player);
            return;
        }


        player.teleport(block.getLocation().add(0, 1, 0));
        player.sendMessage(Settings.PREFIX + "Du wurdest an die §fhöchste Stelle §7teleportiert");
    }

}