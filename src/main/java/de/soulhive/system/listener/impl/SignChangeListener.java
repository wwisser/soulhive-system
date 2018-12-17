package de.soulhive.system.listener.impl;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignChangeListener implements Listener {

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        final Player player = event.getPlayer();

        if (player.hasPermission("soulhive.sign.color")) {
            String[] lines = event.getLines();
            for (int i = 0; i < lines.length; i++) {
                String line = lines[i];
                event.setLine(i, ChatColor.translateAlternateColorCodes('&', line));
            }
        }
    }

}
