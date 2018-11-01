package de.soulhive.system.command.impl.admin;

import de.skycade.system.setting.Message;
import de.skycade.system.util.ActionBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSpeed implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label,
                             String[] args) {
        if (!(commandSender instanceof Player)) {
            System.out.println(Message.COMMAND_ONLY_PLAYER);
            return true;
        }

        Player player = (Player) commandSender;

        if (args.length < 1) {
            player.sendMessage(Message.COMMAND_USAGE + "/speed <Wert>");
            return true;
        }

        if (player.hasPermission("skycade.speed")) {
            float userSpeed = Float.valueOf(args[0]);

            if (userSpeed > 10.0F) {
                userSpeed = 10.0F;
            } else if (userSpeed < 1.0E-004F) {
                userSpeed = 1.0E-004F;
            }

            float defaultSpeed = player.isFlying() ? 0.1F : 0.2F;
            float maxSpeed = 1.0F;
            float ratio = (userSpeed - 1.0F) / 9.0F * (maxSpeed - defaultSpeed);
            float speed = ratio + defaultSpeed;

            if (player.isFlying()) {
                player.setFlySpeed(speed);
            } else {
                player.setWalkSpeed(speed);
            }
            ActionBar.send("§7Dein §3" + ( player.isFlying() ? "Fly" : "Walk") + "speed §7ist jetzt auf §3" + userSpeed, player);
        } else {
            ActionBar.send(Message.NO_PERMISSION, player);
        }
        return true;
    }
}
