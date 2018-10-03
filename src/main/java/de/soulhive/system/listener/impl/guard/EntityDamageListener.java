package de.soulhive.system.listener.impl.guard;

import de.soulhive.system.setting.Settings;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof ItemFrame
            && !Settings.SKYBLOCK_WORLDS.contains(entity.getWorld())
            && event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
            event.setCancelled(true);
        }
    }

}