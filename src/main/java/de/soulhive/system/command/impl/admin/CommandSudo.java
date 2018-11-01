package de.soulhive.system.command.impl.admin;

import de.skycade.system.setting.Message;
import de.skycade.system.util.ActionBar;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSudo implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender.hasPermission("skycade.sudo")) {
            if (args.length < 2) {
                commandSender.sendMessage(Message.COMMAND_USAGE + "/sudo <player> <message|command>");
                return true;
            }
            Player player = Bukkit.getPlayer(args[0]);

            if (player != null && player.isOnline()) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    stringBuilder.append(args[i]).append(" ");
                }
                String input = stringBuilder.toString();
                player.chat(input);
                commandSender.sendMessage(Message.PREFIX_TEAM + "Für " + player.getName() + " ausgegeben: §a" + input);
            } else {
                commandSender.sendMessage(Message.PREFIX + "§cSpieler nicht gefunden.");
            }
        } else {
            ActionBar.send(Message.NO_PERMISSION, (Player) commandSender);
        }
        return true;
    }

}
