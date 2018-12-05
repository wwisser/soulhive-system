package de.soulhive.system.container;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class ContainerEntry {

    private int inventorySlot;
    private ItemStack itemStack;

}
