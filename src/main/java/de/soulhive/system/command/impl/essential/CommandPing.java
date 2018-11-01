package de.soulhive.system.command.impl.essential;

import de.skycade.system.setting.Message;
import de.skycade.system.util.ActionBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class CommandPing implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) {
            System.out.println(Message.COMMAND_ONLY_PLAYER);
            return true;
        }

        Player player = (Player) commandSender;
        int ping = ((CraftPlayer) player).getHandle().ping;

        if (ping < 3 || ping > 999) {
            ping = 999;
        }

        String level;

        if (ping < 32) {
            level = "§aSehr gut";
        } else if (ping < 70) {
            level = "§aGut";
        } else if (ping < 150) {
            level = "§eOkay";
        } else if (ping > 250) {
            level = "§cSehr Schlecht";
        } else {
            level = "§cSchlecht";
        }

        ActionBar.send("§7Dein Ping liegt bei §3" + ping + " §8(" + level + "§8)", player);

        return true;
    }
}

