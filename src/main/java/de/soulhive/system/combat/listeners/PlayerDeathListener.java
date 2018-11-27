package de.soulhive.system.combat.listeners;

import de.soulhive.system.combat.CombatService;
import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

@AllArgsConstructor
public class PlayerDeathListener implements Listener {

    private CombatService combatService;

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        this.combatService.detachFight(event.getEntity(), false);
    }

}
