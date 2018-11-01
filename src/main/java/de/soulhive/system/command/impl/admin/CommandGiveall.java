package de.soulhive.system.command.impl.admin;

import de.skycade.system.setting.Message;
import de.skycade.system.util.ActionBar;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandGiveall implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) {
            System.out.println(Message.COMMAND_ONLY_PLAYER);
            return true;
        }

        Player player = (Player) commandSender;

        if (player.hasPermission("novasky.giveall")) {
            if (player.getItemInHand() != null && !player.getItemInHand().getType().equals(Material.AIR)) {
                ItemStack itemStack = player.getItemInHand().clone();

                Bukkit.getOnlinePlayers()
                        .stream()
                        .filter(players -> players != player)
                        .forEach(players -> {
                            players.getInventory().addItem(itemStack);
                            players.playSound(players.getLocation(), Sound.HORSE_SADDLE, 1, 1);
                        });
                Bukkit.broadcastMessage(Message.PREFIX + "§7Jeder Spieler hat " + itemStack.getAmount() + "x §3" + itemStack.getType().toString()
                + " §7erhalten.");
            } else {
                ActionBar.send("§cDu musst ein Item in deiner Hand halten.", player);
            }
        } else {
            ActionBar.send(Message.NO_PERMISSION, player);
        }
        return true;
    }
}
