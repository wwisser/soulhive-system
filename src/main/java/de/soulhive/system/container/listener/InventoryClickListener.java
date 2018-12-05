package de.soulhive.system.container.listener;

import de.soulhive.system.container.Container;
import de.soulhive.system.container.ContainerService;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class InventoryClickListener implements Listener {

    private ContainerService containerService;

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        Player player = (Player) event.getWhoClicked();
        ItemStack currentItem = event.getCurrentItem();

        for (Container container : this.containerService.getContainers()) {
            if (!inventory.getName().equals(container.getName())) {
                continue;
            }
            event.setCancelled(true);

            if (currentItem != null) {
                container.getActions().keySet()
                    .stream()
                    .filter(containerEntry -> containerEntry.getItemStack().equals(currentItem))
                    .forEach(itemStack -> container.getActions().get(itemStack).process(player));
            }
        }
    }

}
