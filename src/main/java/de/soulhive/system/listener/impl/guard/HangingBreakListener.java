package de.soulhive.system.listener.impl.guard;

import de.soulhive.system.setting.Settings;
import org.bukkit.entity.Hanging;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakEvent;

public class HangingBreakListener implements Listener {

    @EventHandler
    public void onHangigBreak(HangingBreakEvent event) {
        Hanging entity = event.getEntity();

        if (entity.getWorld().equals(Settings.WORLD_MAIN) && entity.getLocation().getBlockY() > Settings.SPAWN_HEIGHT) {
            event.setCancelled(true);
        }
    }

}
