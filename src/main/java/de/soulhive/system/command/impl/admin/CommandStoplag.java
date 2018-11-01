package de.soulhive.system.command.impl.admin;

import de.skycade.system.setting.Message;
import de.skycade.system.util.ActionBar;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandStoplag implements CommandExecutor {

    @Getter private static World world;
    @Getter private static boolean enabled = false;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("skycade.stoplag")) {
            if (sender instanceof Player) {
                if (enabled) {
                    Bukkit.getOnlinePlayers().stream().filter(player -> player.isOp()).forEach(
                            player -> player.sendMessage("§9§lSkyCade §7Stoplag wurde §cdeaktiviert§7.")
                    );
                    enabled = false;
                } else {
                    Bukkit.getOnlinePlayers().stream().filter(player -> player.isOp()).forEach(
                            player -> player.sendMessage("§9§lSkyCade §7Stoplag wurde §aaktiviert§7.")
                    );
                    enabled = true;
                    world = ((Player) sender).getWorld();
                }
            } else {
                sender.sendMessage(Message.COMMAND_ONLY_PLAYER);
            }
        } else {
            ActionBar.send(Message.NO_PERMISSION, (Player) sender);
        }
        return true;
    }

}