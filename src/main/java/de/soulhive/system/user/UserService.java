package de.soulhive.system.user;

import de.soulhive.system.service.Service;
import de.soulhive.system.user.helper.UserHelper;
import de.soulhive.system.user.listeners.PlayerJoinListener;
import de.soulhive.system.user.listeners.PlayerQuitListener;
import de.soulhive.system.user.repository.UserRepository;
import de.soulhive.system.user.repository.impl.FileUserRepository;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Getter
public class UserService extends Service {

    private Map<Player, User> onlineCache = new HashMap<>();
    private UserRepository userRepository = new FileUserRepository();
    private UserHelper userHelper;

    @Override
    public void initialize() {
        super.registerListeners(
            new PlayerJoinListener(this),
            new PlayerQuitListener(this)
        );

        Bukkit.getOnlinePlayers().forEach(this::loadUser);
        this.userHelper = UserHelper.getInstance();
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

        user.setLastSeen(System.currentTimeMillis());
        this.userRepository.save(user);
        this.onlineCache.remove(player);
    }

    public User getUser(Player player) {
        return this.onlineCache.get(player);
    }

    public User getUserByName(String name) {
        List<User> result = this.filterCache(user -> user.getName().equalsIgnoreCase(name));

        if (result.isEmpty()) {
            try {
                return this.userRepository.fetchByName(name);
            } catch (IllegalArgumentException e) {
                return null;
            }
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

    private List<User> filterCache(Predicate<? super User> predicate) {
        return this.onlineCache.values()
            .stream()
            .filter(predicate)
            .collect(Collectors.toList());
    }

}
