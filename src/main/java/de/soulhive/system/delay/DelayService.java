package de.soulhive.system.delay;

import de.soulhive.system.service.Service;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.util.MillisecondsConverter;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.function.Consumer;

public class DelayService extends Service {

    private Map<DelayConfiguration, UUID> configurations = new HashMap<>();
    private Map<UUID, Map<UUID, Long>> delays = new HashMap<>();

    public void handleDelay(Player player, DelayConfiguration configuration, Consumer<Player> action) {
        this.handle(player, configuration, action, false);
    }

    public void handleDelayInverted(Player player, DelayConfiguration configuration, Consumer<Player> action) {
        this.handle(player, configuration, action, true);
    }

    private void handle(Player player, DelayConfiguration configuration, Consumer<Player> action, boolean inverted) {
        UUID uuid = player.getUniqueId();

        if (!this.configurations.containsKey(configuration)) {
            this.configurations.put(configuration, UUID.randomUUID());
            if (!inverted) {
                action.accept(player);
            }
            this.addDelay(uuid, configuration);
            return;
        }

        if (!this.delays.containsKey(uuid)) {
            if (!inverted) {
                action.accept(player);
            }
            this.addDelay(uuid, configuration);
            return;
        }

        final UUID configUuid = this.configurations.get(configuration);
        Map<UUID, Long> delayEntry = this.delays.getOrDefault(uuid, new HashMap<>());

        if (delayEntry.containsKey(configUuid)) {
            long endTimeStamp = delayEntry.get(configUuid);

            if (System.currentTimeMillis() >= endTimeStamp) {
                if (!inverted) {
                    action.accept(player);
                }
                this.addDelay(uuid, configuration);
            } else if (configuration.getMessage() != null) {
                long pendingTime = endTimeStamp - System.currentTimeMillis();
                String formattedTime = MillisecondsConverter.convertToString(pendingTime);

                if (!inverted) {
                    player.sendMessage(
                        Settings.PREFIX + "§c" + configuration.getMessage().replace("%time", formattedTime)
                    );
                } else {
                    action.accept(player);
                }
            }
        }
    }

    private void addDelay(UUID uuid, DelayConfiguration delayConfiguration) {
        Map<UUID, Long> delayEntry = this.delays.getOrDefault(uuid, new HashMap<>());

        delayEntry.put(
            this.configurations.get(delayConfiguration),
            System.currentTimeMillis() + delayConfiguration.getTime()
        );

        this.delays.put(uuid, delayEntry);
    }

}
