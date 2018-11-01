package de.soulhive.system.listener.impl.item;

import de.soulhive.system.util.nms.ActionBar;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();

        if (event.getInventory().getType().equals(InventoryType.ENCHANTING)
            && event.getRawSlot() == 1) {
            event.setCancelled(true);
        }

        if (!(inventory instanceof AnvilInventory)) {
            return;
        }

        InventoryView inventoryView = event.getView();
        int rawSlot = event.getRawSlot();

        if (rawSlot == inventoryView.convertSlot(rawSlot) && rawSlot == 2) {
            ItemStack itemStack = event.getCurrentItem();

            if (itemStack != null
                && itemStack.getType() != Material.AIR
                && itemStack.getAmount() > itemStack.getMaxStackSize()) {

                event.setCancelled(true);
                player.closeInventory();
                ActionBar.send("§cDu darfst keine gestackten Items im Amboss verwenden.", player);
                player.playSound(player.getLocation(), Sound.CREEPER_HISS, 1, 1);
            }
        }
    }

}
