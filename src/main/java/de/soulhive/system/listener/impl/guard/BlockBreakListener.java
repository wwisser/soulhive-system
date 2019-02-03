package de.soulhive.system.listener.impl.guard;

import de.soulhive.system.setting.Settings;
import de.soulhive.system.util.nms.ActionBar;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (!Settings.SKYBLOCK_WORLDS.contains(player.getWorld()) && !player.hasPermission("soulhive.build")) {
            ActionBar.send("§cDu darfst hier nichts zerstören.", player);
            event.setCancelled(true);
        }

        if (!Settings.SKYBLOCK_WORLDS.contains(player.getWorld()) && player.getGameMode() == GameMode.SURVIVAL) {
            event.setCancelled(true);
        }
    }

}
