package de.soulhive.system.listener.impl.guard;

import de.soulhive.system.setting.Settings;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevelChangeListener implements Listener {

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        Player player = (Player) event.getEntity();

        if (player.getWorld().equals(Settings.WORLD_MAIN)
            && player.getLocation().getBlockY() > Settings.SPAWN_HEIGHT
            && event.getFoodLevel() < player.getFoodLevel()) {
            event.setCancelled(true);
        }
    }

}
