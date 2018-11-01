package de.soulhive.system.command.impl.essential;

import de.skycade.system.SkyCade;
import de.skycade.system.setting.Message;
import de.skycade.system.util.ActionBar;
import de.skycade.system.util.CooldownManager;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandFeed implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label,
                             String[] args) {
        if (!(commandSender instanceof Player)) {
            System.out.println(Message.COMMAND_ONLY_PLAYER);
            return true;
        }

        Player player = (Player) commandSender;

        if (player.hasPermission("skycade.feed")) {
            CooldownManager cooldownManager = SkyCade.getCooldownManager();
            long cooldown = 60000;

            String COOLDOWN_ID = "FEED";
            if (!cooldownManager.isCooldowned(player, COOLDOWN_ID)) {
                player.setFoodLevel(20);
                player.playSound(player.getLocation(), Sound.BURP, 1, 1);
                ActionBar.send("§7Du wurdest §3gesättigt§7!", player);
                cooldownManager.addPlayer(player, COOLDOWN_ID, cooldown);
            } else {
                ActionBar.send("§cBitte warte noch " + cooldownManager.toSeconds(player, COOLDOWN_ID), player);
            }
        } else {
            ActionBar.send(Message.NO_PERMISSION, (Player) commandSender);
        }

        return true;
    }
}