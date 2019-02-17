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

    public boolean handleDelay(Player player, DelayConfiguration configuration, Consumer<Player> action) {
        return this.handle(player, configuration, action, false);
    }

    public boolean handleDelayInverted(Player player, DelayConfiguration configuration, Consumer<Player> action) {
        return this.handle(player, configuration, action, true);
    }

    /**
     * @return true if is delayed
     */
    private boolean handle(Player player, DelayConfiguration configuration, Consumer<Player> action, boolean inverted) {
        UUID uuid = player.getUniqueId();

        if (!this.configurations.containsKey(configuration)) {
            this.configurations.put(configuration, UUID.randomUUID());
            if (!inverted) {
                action.accept(player);
            }
            this.addDelay(uuid, configuration);
            return false;
        }

        if (!this.delays.containsKey(uuid)) {
            if (!inverted) {
                action.accept(player);
            }
            this.addDelay(uuid, configuration);
            return false;
        }

        final UUID configUuid = this.configurations.get(configuration);
        Map<UUID, Long> delayEntry = this.delays.getOrDefault(uuid, new HashMap<>());

        if (delayEntry.containsKey(configUuid)) {
            long endTimeStamp = delayEntry.getOrDefault(configUuid, System.currentTimeMillis());

            if (System.currentTimeMillis() >= endTimeStamp) {
                if (!inverted) {
                    action.accept(player);
                }
                this.addDelay(uuid, configuration);
                return false;
            } else {
                if (!inverted) {
                    if (configuration.getMessage() != null) {
                        long pendingTime = endTimeStamp - System.currentTimeMillis();
                        String formattedTime = MillisecondsConverter.convertToString(pendingTime);
                        player.sendMessage(
                            Settings.PREFIX + "§c" + configuration.getMessage().replace("%time", formattedTime)
                        );
                    }
                } else {
                    action.accept(player);
                }
                return true;
            }
        } else {
            delayEntry.put(configUuid, System.currentTimeMillis() + configuration.getTime());
            this.delays.put(uuid, delayEntry);

            if (!inverted) {
                action.accept(player);
            }

            return false;
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
