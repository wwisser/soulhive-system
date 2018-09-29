package de.skydust.system.user.listeners;

import de.skydust.system.user.service.UserService;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

@AllArgsConstructor
public class PlayerQuitListener implements Listener {

    private UserService userService;

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        this.userService.getUser(player).setLastSeen(System.currentTimeMillis());
        this.userService.unloadUser(player);
    }

}
