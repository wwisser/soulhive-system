package de.soulhive.system.listener.impl.guard;

import de.soulhive.system.setting.Settings;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;

public class LeavesDecayListener implements Listener {

    @EventHandler
    public void onLeavesDecay(LeavesDecayEvent event) {
        if (!event.getBlock().getWorld().equals(Settings.WORLD_SKYBLOCK)) {
            event.setCancelled(true);
        }
    }

}
