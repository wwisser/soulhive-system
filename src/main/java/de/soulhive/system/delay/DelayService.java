package de.soulhive.system.delay;

import de.soulhive.system.service.Service;
import de.soulhive.system.setting.Settings;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.function.Consumer;

public class DelayService extends Service {

    private List<DelayConfiguration> configurations = new LinkedList<>();
    private Map<UUID, Map<Integer, Long>> delays = new HashMap<>();

    public void handleDelay(Player player, DelayConfiguration configuration, Consumer<Player> action) {
        UUID uuid = player.getUniqueId();

        if (!this.configurations.contains(configuration)) {
            this.configurations.add(configuration);
            action.accept(player);
            this.addDelay(uuid, configuration);
            return;
        }

        int configIndex = this.configurations.indexOf(configuration);
        Map<Integer, Long> delayEntry = this.delays.getOrDefault(uuid, new HashMap<>());

        if (delayEntry.containsKey(configIndex)) {
            long endTimeStamp = delayEntry.get(configIndex);

            if (System.currentTimeMillis() > endTimeStamp) {
                action.accept(player);
                this.addDelay(uuid, configuration);
            } else if (configuration.getMessage() != null) {
                player.sendMessage(Settings.PREFIX + configuration.getMessage());
            }
        }
    }

    private void addDelay(UUID uuid, DelayConfiguration delayConfiguration) {
        Map<Integer, Long> delayEntry = this.delays.getOrDefault(uuid, new HashMap<>());

        delayEntry.put(
            this.configurations.indexOf(delayConfiguration),
            System.currentTimeMillis() + delayConfiguration.getTime()
        );

        this.delays.put(uuid, delayEntry);
    }

}
