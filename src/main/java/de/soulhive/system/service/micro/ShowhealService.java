package de.soulhive.system.service.micro;

import de.soulhive.system.service.FeatureService;
import de.soulhive.system.service.Service;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

@FeatureService
public class ShowhealService extends Service implements Listener {

    @Override
    public void initialize() {
        super.registerListener(this);
    }

    @EventHandler
    public void onEntityDamageByEntity(final EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Projectile) {
            final Projectile bullet = (Projectile) event.getDamager();

            if (bullet.getShooter() instanceof Player && event.getEntity() instanceof Player) {
                final Player victim = (Player) event.getEntity();
                final Player damager = (Player) bullet.getShooter();

                String message = this.getHealth(victim);
                ChatColor prefix;

                if (message.length() > 7) {
                    prefix = ChatColor.GREEN;
                } else if (message.length() > 4) {
                    prefix = ChatColor.GOLD;
                } else {
                    prefix = ChatColor.DARK_RED;
                }

                damager.sendMessage(ChatColor.DARK_AQUA + victim.getName() + "'s Leben: " + prefix + message);
            }
        }

        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            final Player victim = (Player) event.getEntity();
            final Player damager = (Player) event.getDamager();
            String message = this.getHealth(victim);

            ChatColor prefix;
            if (message.length() > 7) {
                prefix = ChatColor.GREEN;
            } else if (message.length() > 4) {
                prefix = ChatColor.GOLD;
            } else {
                prefix = ChatColor.DARK_RED;
            }

            damager.sendMessage(ChatColor.DARK_AQUA + victim.getName() + "'s Leben: " + prefix + message);
        }
    }

    private String getHealth(final Player player) {
        StringBuilder result = new StringBuilder();

        if (player.getHealth() % 2 != 0) {
            for (int i = 0; i < (player.getHealth() - 1) / 2; ++i) {
                result.append("\u2764");
            }
            result.append("\u2665");
        } else {
            for (int i = 0; i < player.getHealth() / 2; ++i) {
                result.append("\u2764");
            }
        }
        return result.toString();
    }

}
