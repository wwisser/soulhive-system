package de.soulhive.system.listener.impl.item;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.inventory.ItemStack;

public class BrewListener implements Listener {

    @EventHandler
    public void onBrew(BrewEvent event) {
        for (ItemStack item : event.getContents().getContents()) {
            if (item.getAmount() > item.getMaxStackSize()) {
                item.setAmount(item.getMaxStackSize());
            }
        }
    }

}
