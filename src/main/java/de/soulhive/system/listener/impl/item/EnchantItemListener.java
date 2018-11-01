package de.soulhive.system.listener.impl.item;

import de.soulhive.system.util.nms.ActionBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemStack;

public class EnchantItemListener implements Listener {

    @EventHandler
    public void onEnchantItem(EnchantItemEvent event) {
        Player player = event.getEnchanter();
        ItemStack item = event.getItem();
        int levelCosts = (player.getLevel() - event.getExpLevelCost()) + (event.whichButton() + 1);

        event.setExpLevelCost(levelCosts);

        if (item.getAmount() > item.getMaxStackSize()) {
            event.setCancelled(true);
            player.closeInventory();
            ActionBar.send("§cDu darfst keine gestackten Items verzaubern.", player);
        }
    }

}
