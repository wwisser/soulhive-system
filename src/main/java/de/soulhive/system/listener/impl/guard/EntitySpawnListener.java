package de.soulhive.system.listener.impl.guard;

import de.soulhive.system.SoulHive;
import de.soulhive.system.npc.NpcService;
import de.soulhive.system.setting.Settings;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class EntitySpawnListener implements Listener {

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        Entity entity = event.getEntity();
        Location location = event.getLocation();
        NpcService npcService = SoulHive.getServiceManager().getService(NpcService.class);

        if (event.getEntityType() != EntityType.PLAYER
            && location.getWorld().equals(Settings.WORLD_MAIN)
            && location.getBlockY() > Settings.SPAWN_HEIGHT
            & !npcService.isNpc(entity)) {
            event.setCancelled(true);
        }
    }

}
