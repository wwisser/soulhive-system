package de.soulhive.system.listener.impl;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(final PlayerDeathEvent event) {
        final Player player = event.getEntity();

        if (player.hasPermission("soulhive.keeplevel")) {
            event.setKeepLevel(true);
            event.setDroppedExp(0);
        }
    }

}
