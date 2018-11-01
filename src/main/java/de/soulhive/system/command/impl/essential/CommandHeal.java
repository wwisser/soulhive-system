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

public class CommandHeal implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label,
                             String[] args) {
        if (!(commandSender instanceof Player)) {
            System.out.println(Message.COMMAND_ONLY_PLAYER);
            return true;
        }

        Player player = (Player) commandSender;

        if (player.hasPermission("skycade.heal")) {
            CooldownManager cooldownManager = SkyCade.getCooldownManager();
            long cooldown = 60000;

            String COOLDOWN_ID = "HEAL";
            if (!cooldownManager.isCooldowned(player, COOLDOWN_ID)) {
                player.setHealth(20);
                player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1, 1);
                ActionBar.send("§7Du wurdest §3geheilt§7!", player);
                cooldownManager.addPlayer(player, COOLDOWN_ID, cooldown);
                if (player.isOp()) {
                    player.setFoodLevel(20);
                }
            } else {
                ActionBar.send("§cBitte warte noch " + cooldownManager.toSeconds(player, COOLDOWN_ID), player);
            }
        } else {
            ActionBar.send(Message.NO_PERMISSION, (Player) commandSender);
        }

        return true;
    }
}