package de.soulhive.system.listener.impl;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class EntityDeathListener implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getType() != EntityType.PIG_ZOMBIE) {
            return;
        }

        List<ItemStack> drops = event.getDrops();

        drops.clear();
        if (ThreadLocalRandom.current().nextInt(100) < 15) {
            drops.add(new ItemStack(Material.GOLD_INGOT));
        }
    }

}
