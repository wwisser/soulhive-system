package de.soulhive.system.listener.impl.guard;

import de.soulhive.system.setting.Settings;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;

public class HangingBreakByEntityListener implements Listener {

    @EventHandler
    public void onHangingBreakByEntity(HangingBreakByEntityEvent event) {
        Entity remover = event.getRemover();

        if (remover.getWorld().equals(Settings.WORLD_MAIN)) {
            if (remover.getLocation().getBlockY() > Settings.SPAWN_HEIGHT) {
                if (!remover.hasPermission("soulhive.build")) {
                    event.setCancelled(true);
                }
            }
        }
    }

}
