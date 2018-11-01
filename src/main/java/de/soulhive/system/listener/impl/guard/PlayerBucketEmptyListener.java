package de.soulhive.system.listener.impl.guard;

import de.soulhive.system.setting.Settings;
import de.soulhive.system.util.nms.ActionBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

public class PlayerBucketEmptyListener implements Listener {

    @EventHandler
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
        Player player = event.getPlayer();

        if (!Settings.SKYBLOCK_WORLDS.contains(player.getWorld()) && !player.hasPermission("skydust.build")) {
            ActionBar.send("§cDu darfst hier nichts platzieren.", event.getPlayer());
            event.setCancelled(true);
        }
    }

}
