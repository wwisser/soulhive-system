package de.soulhive.system.clan.listener;

import de.soulhive.system.clan.ClanService;
import de.soulhive.system.setting.Settings;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

@AllArgsConstructor
public class EntityDamageByEntityListener implements Listener {

    private ClanService clanService;

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!event.getEntity().getWorld().equals(Settings.WORLD_MAIN) || !(event.getEntity() instanceof Player)) {
            return;
        }
        Entity damager = event.getDamager();
        Player victim = (Player) event.getEntity();

        if (damager instanceof Player && damager != victim) {
            this.clanService.getLastHits().put(victim, (Player) damager);
        }

        if (!(damager instanceof Projectile)) {
            return;
        }

        Projectile projectile = (Projectile) damager;

        if (projectile.getShooter() instanceof Player) {
            Player shooter = (Player) projectile.getShooter();

            if (shooter != victim) {
                this.clanService.getLastHits().put(victim, shooter);
            }
        }

    }

}
