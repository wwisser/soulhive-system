package de.soulhive.system.combat.listeners;

import de.soulhive.system.combat.CombatService;
import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

@AllArgsConstructor
public class PlayerQuitListener implements Listener {

    private CombatService combatService;

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        this.combatService.detachFight(event.getPlayer(), true);
    }

}
