package de.skydust.system.user.listeners;

import de.skydust.system.user.repository.UserRepository;
import de.skydust.system.user.repository.impl.FileUserRepository;
import de.skydust.system.user.service.UserService;
import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

@AllArgsConstructor
public class PlayerQuitListener implements Listener {

    private UserService userService;

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        UserRepository userRepository = this.userService.getUserRepository();

        if (userRepository instanceof FileUserRepository) {
            ((FileUserRepository) userRepository).updateUuid(event.getPlayer());
        }

        this.userService.getOnlineCache().remove(event.getPlayer());
    }

}
