package de.soulhive.system.npc.listener;

import de.soulhive.system.npc.NpcService;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

@AllArgsConstructor
public class PlayerInteractAtEntityListener implements Listener {

    private NpcService npcService;

    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        final Entity rightClicked = event.getRightClicked();

        this.npcService.getNpcs()
            .stream()
            .filter(npc -> npc.getEntity().getUniqueID().equals(rightClicked.getUniqueId()))
            .forEach(npc -> npc.getClickAction().accept(event.getPlayer()));
    }

}
