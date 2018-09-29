package de.skydust.system.user.service;

import de.skydust.system.service.Service;
import de.skydust.system.user.User;
import de.skydust.system.user.listeners.PlayerJoinListener;
import de.skydust.system.user.repository.UserRepository;
import de.skydust.system.user.repository.impl.FileUserRepository;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Getter
public class UserService implements Service {

    private Map<Player, User> onlineCache = new HashMap<>();
    private UserRepository userRepository = new FileUserRepository();

    @Override
    public Set<Listener> getListeners() {
        return Collections.singleton(
            new PlayerJoinListener(this)
        );
    }

}
