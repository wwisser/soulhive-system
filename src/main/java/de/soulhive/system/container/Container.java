package de.soulhive.system.container;

import lombok.*;
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
    private Map<ContainerEntry, ContainerAction> actions;

    @RequiredArgsConstructor
    public static class ContainerBuilder {

        @NonNull private String name;
        private Map<ContainerEntry, ContainerAction> actions = new HashMap<>();

        public ContainerBuilder withAction(int slot, ItemStack itemStack, ContainerAction action) {
            this.actions.put(new ContainerEntry(slot, itemStack), action);

            return this;
        }

        public Container build() {
            if (this.actions.isEmpty()) {
                throw new UnsupportedOperationException("Container must have at least 1 entry");
            }

            return new Container(this.name, this.actions);
        }

    }

}
