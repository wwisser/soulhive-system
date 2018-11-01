package de.soulhive.system.command.impl.essential;

import de.skycade.system.setting.Message;
import de.skycade.system.util.ActionBar;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandEnderchest implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) {
            System.out.println(Message.COMMAND_ONLY_PLAYER);
            return true;
        }

        Player player = (Player) commandSender;

        if (player.hasPermission("skycade.enderchest")) {
            if (player.hasPermission("skycade.enderchest.target")) {
                if (args.length < 1) {
                    player.openInventory(player.getEnderChest());
                    player.playSound(player.getLocation(), Sound.CHEST_OPEN, 1, 1);
                } else {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null && target.isOnline()) {
                        player.openInventory(target.getEnderChest());
                        player.playSound(player.getLocation(), Sound.CHEST_OPEN, 1, 1);
                    } else {
                        ActionBar.send("§cSpieler nicht gefunden", player);
                    }
                }
            } else {
                player.openInventory(player.getEnderChest());
                player.playSound(player.getLocation(), Sound.CHEST_OPEN, 1, 1);
            }
        } else {
            ActionBar.send(Message.NO_PERMISSION, player);
        }

        return true;
    }
}