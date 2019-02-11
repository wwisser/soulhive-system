package de.soulhive.system.kit.listener;

import de.soulhive.system.kit.KitService;
import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

@AllArgsConstructor
public class PlayerRespawnListener implements Listener {

    private KitService kitService;

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        this.kitService.equipWithStarter(event.getPlayer());
    }

}
