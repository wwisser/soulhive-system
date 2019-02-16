package de.soulhive.system.listener.impl.guard;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import de.soulhive.system.setting.Settings;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {

    private ASkyBlockAPI aSkyBlockApi = ASkyBlockAPI.getInstance();

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof ItemFrame
            && !Settings.SKYBLOCK_WORLDS.contains(entity.getWorld())
            && event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
            event.setCancelled(true);
        }

        if (entity instanceof Player && event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
            event.setCancelled(true);
        }

        if (entity instanceof Player
            && event.getCause() == EntityDamageEvent.DamageCause.VOID
            && Settings.SKYBLOCK_WORLDS.contains(entity.getWorld())) {
            event.setCancelled(true);
            entity.teleport(Settings.LOCATION_SPAWN);
            entity.sendMessage(Settings.PREFIX + "Das war aber knapp! Pass nächstes Mal besser auf.");
        }
    }

}