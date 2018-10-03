package de.soulhive.system.listener.impl;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.playSound(player.getLocation(), Sound.LEVEL_UP, Float.MAX_VALUE, Float.MIN_VALUE);
        event.setJoinMessage(null);
    }

}
