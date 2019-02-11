package de.soulhive.system.combat.listeners;

import de.soulhive.system.combat.CombatService;
import de.soulhive.system.setting.Settings;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

@AllArgsConstructor
public class PlayerCommandPreprocessListener implements Listener {

    private CombatService combatService;

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String command = event.getMessage();

        if (this.combatService.isFighting(player) && !this.combatService.isCommandAllowed(command)) {
            event.setCancelled(true);
            player.sendMessage(Settings.PREFIX + "§cDu darfst diesen Befehl im Kampf nicht verwenden.");
        }

        if (this.combatService.isCommandTeleportable(command) && player.getLocation().getBlockY() < Settings.SPAWN_HEIGHT) {
            event.setCancelled(true);
            player.sendMessage(Settings.PREFIX + "§cDu darfst diesen Befehl hier nicht verwenden.");
        }

    }

}
