package de.soulhive.system.listener.impl.guard;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class InventoryClickListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getInventory();

        if (!(inv instanceof AnvilInventory)) {
            return;
        }

        InventoryView view = event.getView();
        int rawSlot = event.getRawSlot();

        if (rawSlot == view.convertSlot(rawSlot) && rawSlot == 2) {
            ItemStack item = event.getCurrentItem();

            if (item != null && item.getItemMeta() != null && item.getItemMeta().hasDisplayName()) {
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(
                    meta.getDisplayName()
                        .replace("卐", "*")
                        .replace("卍", "*")
                );

                List<String> lore = new ArrayList<>();
                if (meta.hasLore()) {
                    for (String line : meta.getLore()) {
                        if (!line.contains("Bearbeitet von")) {
                            lore.add(line);
                        }
                    }
                }
                lore.add("§7§oBearbeitet von " + player.getName());
                meta.setLore(lore);
                item.setItemMeta(meta);
            }
        }
    }

}
