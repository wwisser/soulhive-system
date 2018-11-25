package de.soulhive.system.command.impl.essential;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.setting.Settings;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class CommandPing extends CommandExecutorWrapper {

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        Player player = ValidateCommand.onlyPlayer(sender);
        int ping = ((CraftPlayer) player).getHandle().ping;

        if (ping < 1 || ping > 999) {
            ping = 999;
        }

        player.sendMessage(Settings.PREFIX + "Dein Ping liegt bei §f" + ping + "ms§7.");
    }

}

