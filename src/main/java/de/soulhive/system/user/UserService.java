package de.soulhive.system.user;

import de.soulhive.system.service.Service;
import de.soulhive.system.user.listeners.PlayerJoinListener;
import de.soulhive.system.user.listeners.PlayerQuitListener;
import de.soulhive.system.user.repository.UserRepository;
import de.soulhive.system.user.repository.impl.FileUserRepository;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Getter
public class UserService extends Service {

    private Map<Player, User> onlineCache = new HashMap<>();
    private UserRepository userRepository = new FileUserRepository();

    @Override
    public void initialize() {
        Bukkit.getOnlinePlayers().forEach(this::loadUser);
    }

    @Override
    public void disable() {
        Bukkit.getOnlinePlayers().forEach(this::unloadUser);
    }

    public User loadUser(Player player) {
        User fetchedUser = this.userRepository.fetchByUuid(player.getUniqueId().toString());

        this.onlineCache.put(player, fetchedUser);
        return fetchedUser;
    }

    public void unloadUser(Player player) {
        User user = this.getUser(player);

        this.userRepository.save(user);
        this.onlineCache.remove(player);
    }

    public User getUser(Player player) {
        return this.onlineCache.get(player);
    }

    public User getUserByName(String name) {
        List<User> result = this.filterCache(user -> user.getName().equals(name));

        if (result.isEmpty()) {
            return this.userRepository.fetchByName(name);
        } else {
            return result.get(0);
        }
    }

    public User getUserByUuid(String uuid) {
        List<User> result = this.filterCache(user -> user.getUuid().equals(uuid));

        if (result.isEmpty()) {
            return this.userRepository.fetchByUuid(uuid);
        } else {
            return result.get(0);
        }
    }

    @Override
    public Set<Listener> getListeners() {
        return new HashSet<Listener>() {{
            super.add(new PlayerJoinListener(UserService.this));
            super.add(new PlayerQuitListener(UserService.this));
        }};
    }

    private List<User> filterCache(Predicate<? super User> predicate) {
        return this.onlineCache.values()
            .stream()
            .filter(predicate)
            .collect(Collectors.toList());
    }

}