package de.skydust.system.user.listeners;

import de.skydust.system.user.repository.UserRepository;
import de.skydust.system.user.repository.impl.FileUserRepository;
import de.skydust.system.user.service.UserService;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@AllArgsConstructor
public class PlayerJoinListener implements Listener {

    private UserService userService;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UserRepository userRepository = this.userService.getUserRepository();

        if (userRepository instanceof FileUserRepository) {
            ((FileUserRepository) userRepository).updateUuid(player);
        }

        this.userService.loadUser(player);
    }

}
