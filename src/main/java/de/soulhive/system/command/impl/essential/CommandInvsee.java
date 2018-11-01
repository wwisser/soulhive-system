package de.soulhive.system.command.impl.essential;

import de.skycade.system.setting.Message;
import de.skycade.system.util.ActionBar;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CommandInvsee implements CommandExecutor {

    @Getter
    private static Map<UUID, Inventory> invsees = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label,
                             String[] args) {
        if (!(commandSender instanceof Player)) {
            System.out.println(Message.COMMAND_ONLY_PLAYER);
            return true;
        }

        Player player = (Player) commandSender;

        if (args.length < 1) {
            player.sendMessage(Message.COMMAND_USAGE + "/invsee <Spieler>");
            return true;
        }

        if (player.hasPermission("skycade.invsee")) {
            Player target = Bukkit.getPlayer(args[0]);

            if (target != null && target.isOnline()) {
                if (target != player) {
                    player.openInventory(target.getInventory());
                    invsees.put(player.getUniqueId(), target.getInventory());
                } else {
                    ActionBar.send("§cDu darfst dein eigenes Inventar nicht öffnen.", player);
                }
            } else {
                ActionBar.send("§cSpieler nicht gefunden.", player);
            }
        } else {
            ActionBar.send(Message.NO_PERMISSION, player);
        }

        return true;
    }
}