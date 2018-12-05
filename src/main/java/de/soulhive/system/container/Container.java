package de.soulhive.system.container;

import lombok.*;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Container {

    /**
     * Can and should contain color codes as the name of the container must be unique.
     */
    private String name;
    private int size;
    private Map<ContainerEntry, ContainerAction> actions;

    public Inventory generateInventory() {
        final Inventory inventory = Bukkit.createInventory(null, this.size, this.name);

        this.actions.keySet().forEach(containerEntry ->
            inventory.setItem(containerEntry.getInventorySlot(), containerEntry.getItemStack())
        );

        return inventory;
    }

    @RequiredArgsConstructor
    public static class ContainerBuilder {

        private static final int DEFAULT_INVENTORY_SIZE = 3 * 9;

        @NonNull private String name;
        private Map<ContainerEntry, ContainerAction> actions = new HashMap<>();
        private int size = DEFAULT_INVENTORY_SIZE;

        public ContainerBuilder withAction(int slot, ItemStack itemStack, ContainerAction action) {
            this.actions.put(new ContainerEntry(slot, itemStack), action);
            return this;
        }

        public ContainerBuilder withSize(int size) {
            this.size = size;
            return this;
        }

        public Container build() {
            if (this.actions.isEmpty()) {
                throw new IllegalArgumentException("Container must have at least 1 entry");
            }
            if (this.actions.size() > this.size) {
                throw new IllegalArgumentException(
                    "There are " + this.actions.size() + " item entries but only " + this.size + " slots"
                );
            }

            return new Container(this.name, this.size, this.actions);
        }

    }

}
