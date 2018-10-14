package de.soulhive.system.user.listeners;

import de.soulhive.system.user.repository.UserRepository;
import de.soulhive.system.user.repository.impl.FileUserRepository;
import de.soulhive.system.user.UserService;
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
