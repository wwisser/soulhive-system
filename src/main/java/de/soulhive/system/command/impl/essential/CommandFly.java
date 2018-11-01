package de.soulhive.system.command.impl.essential;

import de.skycade.system.setting.Message;
import de.skycade.system.util.ActionBar;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandFly implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) {
            System.out.println(Message.COMMAND_ONLY_PLAYER);
            return true;
        }

        Player player = (Player) commandSender;
        boolean flying = player.isFlying();

        if (player.hasPermission("skycade.fly")) {
            player.setAllowFlight(!flying);
            player.setFlying(!flying);
            ActionBar.send("§7Dein Flugmodus wurde §3" + (!flying ? "§aaktiviert" : "§cdeaktiviert"), player);

            if (!flying) {
                player.playSound(player.getLocation(), Sound.ENDERDRAGON_WINGS, 1, 1);
            }
        } else {
            ActionBar.send(Message.NO_PERMISSION, (Player) commandSender);
        }

        return true;
    }
}