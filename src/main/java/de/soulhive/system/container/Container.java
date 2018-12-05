package de.soulhive.system.container;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.bukkit.inventory.ItemStack;

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
    private Map<ItemStack, ContainerAction> actions;

}
