package de.soulhive.system.command.impl.essential;

import de.skycade.system.setting.Message;
import de.skycade.system.util.ActionBar;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CommandBodysee implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label,
                             String[] args) {
        if (!(commandSender instanceof Player)) {
            System.out.println(Message.COMMAND_ONLY_PLAYER);
            return true;
        }

        Player player = (Player) commandSender;

        if (args.length < 1) {
            player.sendMessage(Message.COMMAND_USAGE + "/bodysee <spieler>");
            return true;
        }

        if (player.hasPermission("skycade.bodysee")) {
            Player target = Bukkit.getPlayer(args[0]);

            if (target != null && target.isOnline()) {
                Inventory inventory = Bukkit.createInventory(null, 9, "§0Rüstung von " + target.getName());

                for (int i = 0; i < target.getInventory().getArmorContents().length; i++) {
                    inventory.setItem(i, target.getInventory().getArmorContents()[i]);
                }

                player.openInventory(inventory);
            } else {
                ActionBar.send("§cSpieler nicht gefunden", player);
            }
        } else {
            ActionBar.send(Message.NO_PERMISSION, player);
        }

        return true;
    }
}