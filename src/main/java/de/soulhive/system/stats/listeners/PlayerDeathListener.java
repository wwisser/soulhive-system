package de.soulhive.system.stats.listeners;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import de.soulhive.system.SoulHive;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.stats.StatsService;
import de.soulhive.system.stats.tasks.PlayerRespawnTask;
import de.soulhive.system.task.impl.HologramAppearanceTask;
import de.soulhive.system.user.User;
import de.soulhive.system.user.UserService;
import lombok.AllArgsConstructor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@AllArgsConstructor
public class PlayerDeathListener implements Listener {

    private StatsService statsService;

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        UserService userService = this.statsService.getUserService();

        Player victim = event.getEntity();
        Player killer = victim.getKiller();

        event.setDeathMessage(null);
        victim.playSound(victim.getLocation(), Sound.BLAZE_DEATH, 1, 1);
        final User user = userService.getUser(victim);

        if (user != null) {
            user.addDeath();
        }

        this.statsService.getTaskService().registerTasks(new PlayerRespawnTask(victim));

        if (killer == null && this.statsService.getLastHits().containsKey(victim)) {
            killer = this.statsService.getLastHits().get(victim);
            this.statsService.getLastHits().remove(victim);
        }

        if (killer != null && killer != victim) {
            User killerUser = userService.getUser(killer);

            if (killerUser != null) {
                killerUser.addKill();
                killerUser.addJewels(5);
            }

            killer.sendMessage(Settings.PREFIX + "Du hast §f" + victim.getName() + " §7getötet! +§f5 Juwelen");
            killer.playSound(killer.getLocation(), Sound.SUCCESSFUL_HIT, 100, 100);
            killer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 10, 5));
            killer.setLevel(killer.getLevel() + 1);
            victim.sendMessage(
                Settings.PREFIX
                    + "Du wurdest von §f"
                    + killer.getName()
                    + " §7mit §c"
                    + this.formatHealth(killer.getHealth())
                    + " ❤ §7getötet."
            );

            final Hologram hologram = HologramsAPI.createHologram(SoulHive.getPlugin(), victim.getLocation());
            hologram.appendTextLine("§9§lKILL §8» §f" + victim.getName());

            new HologramAppearanceTask(hologram.getLocation(), hologram).runTaskTimer(SoulHive.getPlugin(), 0, 3);
        }

        this.statsService.getLastHits().remove(victim);
    }

    private String formatHealth(double health) {
        return String.valueOf(Math.round((health / 2) * 100) / 100);
    }

}
