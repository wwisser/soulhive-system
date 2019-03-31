package de.soulhive.system.npc.listener;

import de.soulhive.system.npc.NpcService;
import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@AllArgsConstructor
public class PlayerInteractEntityListener implements Listener {

    private NpcService npcService;

    @EventHandler
    public void onPlayerInteractEntity(final org.bukkit.event.player.PlayerInteractEntityEvent event) {
        if (this.npcService.isNpc(event.getRightClicked())) {
            event.setCancelled(true);
        }
    }

}
