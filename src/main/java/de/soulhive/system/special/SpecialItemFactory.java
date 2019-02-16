package de.soulhive.system.special;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public interface SpecialItemFactory {

    ItemStack createItem(Player player);

    int getCosts();

    void execute(Player player);

    boolean shouldExecute(ItemStack itemStack, Action action);

    String getUniqueName();

    long getDelay();

    default String getDatabaseEntry() {
        return this.getUniqueName().toLowerCase();
    }

}
