package de.soulhive.system.supply.listener;

import de.soulhive.system.setting.Settings;
import de.soulhive.system.supply.SupplyService;
import lombok.AllArgsConstructor;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

@AllArgsConstructor
public class BlockBreakListener implements Listener {

    private SupplyService supplyService;

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        final Block block = event.getBlock();

        if (this.supplyService.getSignLocations().contains(block.getLocation())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(
                Settings.PREFIX + "§4Das ist ein Supply-Schild." +
                    " Verwende /supply remove, um dieses Schild zu entfernen."
            );
        }
    }

}
