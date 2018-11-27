package de.soulhive.system.combat.task;

import de.soulhive.system.combat.CombatService;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.task.ComplexTask;
import lombok.AllArgsConstructor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

@AllArgsConstructor
public class CombatUpdateTask extends BukkitRunnable implements ComplexTask {

    private static final long PERIOD = 5L;

    private CombatService combatService;

    @Override
    public void setup(JavaPlugin plugin) {
        super.runTaskTimer(plugin, PERIOD, PERIOD);
    }

    @Override
    public void run() {
        for (Player player : this.combatService.getPlayersInFight()) {
            if (player.hasPermission("soulhive.combatlog.bypass")) {
                continue;
            }
            if (this.combatService.isUpdated(this.combatService.getStartTimeStamp(player))) {
                player.sendMessage(Settings.PREFIX + "Du bist nicht mehr im Kampf und darfst dich sicher ausloggen.");
                continue;
            }

            if (player.isInsideVehicle() && player.getVehicle() instanceof LivingEntity) {
                player.getVehicle().remove();
            }

            player.setAllowFlight(false);
            player.setFlying(false);
        }
    }

}
