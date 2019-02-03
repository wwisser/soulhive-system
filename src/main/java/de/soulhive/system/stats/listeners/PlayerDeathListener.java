package de.soulhive.system.stats.listeners;

import de.soulhive.system.setting.Settings;
import de.soulhive.system.stats.StatsService;
import de.soulhive.system.stats.tasks.PlayerRespawnTask;
import de.soulhive.system.user.User;
import de.soulhive.system.user.UserService;
import lombok.AllArgsConstructor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

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

        if (killer == null) {
            killer = this.statsService.getLastHits().get(victim);
        }

        if (killer != null) {
            User killerUser = userService.getUser(killer);

            killerUser.addKill();
            killerUser.addJewels(5);
            killer.sendMessage(Settings.PREFIX + "Du hast §f" + victim.getName() + " §7getötet! +§f5 Juwelen");
            killer.playSound(killer.getLocation(), Sound.SUCCESSFUL_HIT, 100, 100);
            killer.setLevel(killer.getLevel() + 1);
            victim.sendMessage(
                Settings.PREFIX
                    + "Du wurdest von §f"
                    + killer.getName()
                    + " §7mit §c"
                    + this.formatHealth(killer.getHealth())
                    + " ❤ §7getötet."
            );
        }

        this.statsService.getLastHits().remove(victim);
    }

    private String formatHealth(double health) {
        return String.valueOf(Math.round((health / 2) * 100) / 100);
    }

}
