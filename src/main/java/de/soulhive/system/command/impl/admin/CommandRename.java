package de.soulhive.system.command.impl.admin;

import de.skycade.system.setting.Message;
import de.skycade.system.util.ActionBar;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CommandRename implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Message.COMMAND_ONLY_PLAYER);
            return true;
        }

        Player player = (Player) commandSender;

        if (!player.hasPermission("skycade.rename")) {
            ActionBar.send(Message.NO_PERMISSION, player);
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(Message.COMMAND_USAGE + "/rename <Name>");
            return true;
        }

        if ((player.getItemInHand() == null) || (player.getItemInHand().getType() == Material.AIR)) {
            ActionBar.send("§cDu musst ein Item in deiner Hand halten.", player);
            return true;
        }

        ItemStack item = player.getItemInHand();
        ItemMeta meta = item.getItemMeta();
        String name = "";

        for (int i = 0; i < args.length; i++) {
            name = name + args[i] + " ";
        }

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        item.setItemMeta(meta);
        ActionBar.send("§7Item umbenannt zu: §3" + meta.getDisplayName(), player);
        return true;
    }
}