package de.soulhive.system.delay;

import de.soulhive.system.service.Service;
import de.soulhive.system.setting.Settings;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class DelayService extends Service {

    private List<DelayConfiguration> configurations = new LinkedList<>();
    private Map<UUID, Map<Integer, Long>> delays = new HashMap<>();

    public void handleDelay(Player player, DelayConfiguration configuration, Consumer<Player> action) {
        this.handle(player, configuration, action, false);
    }

    public void handleDelayInverted(Player player, DelayConfiguration configuration, Consumer<Player> action) {
        this.handle(player, configuration, action, true);
    }

    private void handle(Player player, DelayConfiguration configuration, Consumer<Player> action, boolean inverted) {
        UUID uuid = player.getUniqueId();

        if (!this.configurations.contains(configuration)) {
            this.configurations.add(configuration);
            if (!inverted) {
                action.accept(player);
            }
            this.addDelay(uuid, configuration);
            return;
        }

        int configIndex = this.configurations.indexOf(configuration);
        Map<Integer, Long> delayEntry = this.delays.getOrDefault(uuid, new HashMap<>());

        if (delayEntry.containsKey(configIndex)) {
            long endTimeStamp = delayEntry.get(configIndex);

            if (System.currentTimeMillis() > endTimeStamp) {
                if (!inverted) {
                    action.accept(player);
                }
                this.addDelay(uuid, configuration);
            } else if (configuration.getMessage() != null) {
                long pendingTime = endTimeStamp - System.currentTimeMillis();
                String formattedTime = this.formatDelay(pendingTime);

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
        Map<Integer, Long> delayEntry = this.delays.getOrDefault(uuid, new HashMap<>());

        delayEntry.put(
            this.configurations.indexOf(delayConfiguration),
            System.currentTimeMillis() + delayConfiguration.getTime()
        );

        this.delays.put(uuid, delayEntry);
    }

    private String formatDelay(long millis) {
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        Set<String> results = new HashSet<>();

        if (hours != 0) {
            results.add(hours + "h");
        }

        if (minutes != 0) {
            results.add(minutes + "min");
        }

        if (seconds != 0) {
            results.add(seconds + "s");
        }

        if (results.isEmpty()) {
            return "1ms";
        }

        return String.join(", ", results);
    }

}
