package de.soulhive.system.listener.impl.guard;

import de.soulhive.system.setting.Settings;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketFillEvent;

public class PlayerBucketFillListener implements Listener {

    @EventHandler
    public void onPlayerBucketFill(final PlayerBucketFillEvent event) {
        Player player = event.getPlayer();

        if (!Settings.SKYBLOCK_WORLDS.contains(player.getWorld()) && !player.hasPermission("soulhive.build")) {
            event.setCancelled(true);
        }
    }

}
