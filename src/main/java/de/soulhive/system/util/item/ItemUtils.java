package de.soulhive.system.util.item;

import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@UtilityClass
public class ItemUtils {

    public void addAndDropRest(Player player, ItemStack... itemStacks) {
        player.getInventory().addItem(itemStacks).values().forEach(itemToDrop ->
                player.getWorld().dropItem(player.getLocation(), itemToDrop));
    }

}
