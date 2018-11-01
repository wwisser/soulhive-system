package de.soulhive.system.listener.impl.item;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.ItemStack;

public class InventoryOpenListener implements Listener {

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (event.getInventory().getType().equals(InventoryType.ENCHANTING)) {
            ((EnchantingInventory) event.getInventory()).setSecondary(
                new ItemStack(Material.INK_SACK, 64, (short) 4)
            );
        }
    }

}
