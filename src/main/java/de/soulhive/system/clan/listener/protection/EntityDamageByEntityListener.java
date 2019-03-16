package de.soulhive.system.clan.listener.protection;


import de.soulhive.system.clan.ClanService;
import de.soulhive.system.peace.PeaceService;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.util.nms.ActionBar;
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
        if (event.getEntity().getWorld().equals(Settings.WORLD_MAIN)) {
            if (!(event.getEntity() instanceof Player)) {
                return;
            }

            Entity damager = event.getDamager();
            Player victim = (Player) event.getEntity();

            if (damager instanceof Player &&
                this.clanService.areMembers(victim, (Player) damager)) {
                event.setCancelled(true);
                ActionBar.send("§c" + victim.getName() + " ist ein Clanmitglied.", damager);
            }

            if (!(damager instanceof Projectile)) {
                return;
            }

            Projectile projectile = (Projectile) damager;

            if (projectile.getShooter() instanceof Player) {
                Player shooter = (Player) projectile.getShooter();

                if (shooter != victim &&
                    this.clanService.areMembers(victim, shooter)) {
                    event.setCancelled(true);
                    ActionBar.send("§c" + victim.getName() + " ist ein Clanmitglied.", shooter);
                }
            }
        }
    }

}