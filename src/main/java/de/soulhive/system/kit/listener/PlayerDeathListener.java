package de.soulhive.system.kit.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(final PlayerDeathEvent event) {
        List<ItemStack> drops = new ArrayList<>(event.getDrops());

        event.getDrops()
            .stream()
            .filter(itemStack -> itemStack.hasItemMeta()
                && itemStack.getItemMeta().hasDisplayName()
                && Arrays.asList("§2§lAutoKit Lvl. I", "§6§lAutoKit Lvl. II")
                .contains(itemStack.getItemMeta().getDisplayName())
            )
            .forEach(drops::remove);

        event.getDrops().clear();
        event.getDrops().addAll(drops);
    }

}
