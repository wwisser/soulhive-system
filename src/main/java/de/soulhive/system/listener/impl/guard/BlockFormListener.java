package de.soulhive.system.listener.impl.guard;

import de.soulhive.system.SoulHive;
import de.soulhive.system.setting.Settings;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;

public class BlockFormListener implements Listener {

    @EventHandler
    public void onBlockForm(BlockFormEvent event) {
        if (event.getBlock().getWorld().equals(Settings.WORLD_MAIN)) {
            event.setCancelled(true);
        }
    }

}
