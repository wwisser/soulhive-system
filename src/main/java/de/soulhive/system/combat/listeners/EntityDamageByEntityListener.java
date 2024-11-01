package de.soulhive.system.combat.listeners;

import de.soulhive.system.combat.CombatService;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

@AllArgsConstructor
public class EntityDamageByEntityListener implements Listener {

    private CombatService combatService;


    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Entity damager = event.getDamager();
        Player victim = (Player) event.getEntity();

        if (damager instanceof Player) {
            this.combatService.setFighting((Player) damager);
            this.combatService.setFighting(victim);
        }

        if (!(damager instanceof Projectile)) {
            return;
        }

        Projectile projectile = (Projectile) damager;

        if (projectile.getShooter() instanceof Player && projectile.getShooter() != victim) {
            this.combatService.setFighting(((Player) projectile.getShooter()));
            this.combatService.setFighting(victim);
        }
    }

}
