package de.soulhive.system.container;

import de.soulhive.system.container.action.ContainerAction;
import lombok.*;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

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
    private ContainerStorageLevel storageLevel;
    private Inventory inventory;

    private Container(ContainerBuilder builder) {
        this.name = builder.name;
        this.size = builder.size;
        this.actions = builder.actions;
        this.storageLevel = builder.storageLevel;
    }

    public Inventory getInventory() {
        if (this.storageLevel == ContainerStorageLevel.NEW) {
            return this.generateInventory();
        } else {
            if (this.inventory == null) {
                return (this.inventory = this.generateInventory());
            } else {
                return this.inventory;
            }
        }
    }

    private Inventory generateInventory() {
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
        private ContainerStorageLevel storageLevel = ContainerStorageLevel.NEW;

        public ContainerBuilder withAction(int slot, ItemStack itemStack, ContainerAction action) {
            this.actions.put(new ContainerEntry(slot, itemStack), action);
            return this;
        }

        public ContainerBuilder withStorageLevel(ContainerStorageLevel storageLevel) {
            this.storageLevel = storageLevel;
            return this;
        }

        public ContainerBuilder withSize(int size) {
            this.size = size;
            return this;
        }

        public Container build() {
            if (this.actions.size() > this.size) {
                throw new IllegalArgumentException(
                    "There are " + this.actions.size() + " item entries but only " + this.size + " slots"
                );
            }

            return new Container(this);
        }

    }

}
