package de.soulhive.system.container.listener;

import de.soulhive.system.container.Container;
import de.soulhive.system.container.ContainerEntry;
import de.soulhive.system.container.ContainerService;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class InventoryClickListener implements Listener {

    private ContainerService containerService;

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        final Inventory inventory = event.getInventory();
        final Player player = (Player) event.getWhoClicked();
        final ItemStack currentItem = event.getCurrentItem();
        final Map<Container, ContainerEntry> elementsToExecute = new HashMap<>(); // stored to prevent ConcurrentModificationException

        for (Container container : this.containerService.getContainers()) {
            if (!inventory.getName().equals(container.getName())) {
                continue;
            }
            event.setCancelled(container.isEventCancelled());

            if (currentItem != null) {
                container.getActions().keySet()
                    .stream()
                    .filter(containerEntry -> containerEntry.getItemStack().equals(currentItem))
                    .forEach(containerEntry -> elementsToExecute.put(container, containerEntry));
            }
        }

        elementsToExecute.entrySet()
            .stream()
            .findFirst()
            .ifPresent(entry -> entry.getKey().getActions().get(entry.getValue()).process(player));
    }

}
