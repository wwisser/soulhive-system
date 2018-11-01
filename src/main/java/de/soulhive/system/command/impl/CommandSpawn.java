package de.soulhive.system.command.impl;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandSpawn implements CommandExecutor {

    private ArrayList<Player> inTeleport = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) {
            System.out.println(Message.COMMAND_ONLY_PLAYER);
            return true;
        }

        Player player = (Player) commandSender;
        Location location = Setting.LOCATION_SPAWN;

        if (this.inTeleport.contains(player)) {
            ActionBar.send("§cEs läuft bereits eine Teleportation.", player);
            return true;
        }

        if (player.hasPermission("novasky.teleport.bypass")) {
            player.teleport(location);
            ActionBar.send("§7Du wurdest zum §3Spawn §7teleportiert.", player);
        } else {
            this.inTeleport.add(player);
            ActionBar.send("§7Teleportation: Bewege dich für §33 Sek. §7nicht.", player);

            new ScheduledTeleport(location, player, 3, SkyCade.getInstance()).teleport(teleported -> {
                if (teleported) {
                    ActionBar.send("§7Du wurdest zum §3Spawn §7teleportiert.", player);
                } else {
                    ActionBar.send("§cTeleportation abgebrochen.", player);
                }
                this.inTeleport.remove(player);
            });
        }

        return true;
    }
}
