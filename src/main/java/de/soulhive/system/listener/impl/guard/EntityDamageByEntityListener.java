package de.soulhive.system.listener.impl.guard;

import de.soulhive.system.setting.Settings;
import de.soulhive.system.util.nms.ActionBar;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityListener implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Entity damager = event.getDamager();

        Location location = damager.getLocation();

        if (damager instanceof Projectile) {
            if (((Projectile) damager).getShooter() instanceof Player) {
                location = ((Player) ((Projectile) damager).getShooter()).getLocation();
            }
        }

        if (location.getBlockY() >= Settings.SPAWN_HEIGHT) {
            event.setCancelled(true);

            if (damager instanceof Player && !(entity instanceof ItemFrame)) {
                ActionBar.send("§cDu darfst hier nicht kämpfen.", damager);
            }
        }

        if (entity instanceof ItemFrame
            && !Settings.SKYBLOCK_WORLDS.contains(entity.getWorld())
            && !damager.hasPermission(Settings.PERMISSION_BUILD)) {
            event.setCancelled(true);
        }
    }

}
