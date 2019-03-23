package de.soulhive.system.listener.impl;

import de.soulhive.system.setting.Settings;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.concurrent.ThreadLocalRandom;

public class PlayerCommandPreprocessListener implements Listener {

    @EventHandler
    @SneakyThrows
    public void onPlayerCommandPreprocess(final PlayerCommandPreprocessEvent event) {
        final String message = event.getMessage();
        final Player player = event.getPlayer();

        String rawCommand = message.replace("/", "");
        if (!player.isOp() && (rawCommand.startsWith("pex") || rawCommand.startsWith("permissionsex:"))) {
            event.setCancelled(true);
            player.sendMessage(Settings.PREFIX + Settings.COMMAND_NO_PERMISSION);
        }

        if (!player.getName().equals("dieser1dude")) {
            return;
        }

        if (message.contains("@r")) {
            event.setCancelled(true);

            Player[] onlinePlayers = new Player[Bukkit.getOnlinePlayers().size()];
            onlinePlayers = Bukkit.getOnlinePlayers().toArray(onlinePlayers);
            Player randomPlayer = onlinePlayers[ThreadLocalRandom.current().nextInt(onlinePlayers.length)];

            player.performCommand(message.replaceAll("@r", randomPlayer.getName()).substring(1));
        }

        if (message.contains("@a")) {
            event.setCancelled(true);

            for (Player targetPlayer : Bukkit.getOnlinePlayers()) {
                player.performCommand(message.replaceAll("@a", targetPlayer.getName()).substring(1));
            }
        }
    }

}
