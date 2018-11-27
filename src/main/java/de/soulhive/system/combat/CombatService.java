package de.soulhive.system.combat;

import de.soulhive.system.SoulHive;
import de.soulhive.system.combat.listeners.EntityDamageByEntityListener;
import de.soulhive.system.combat.listeners.PlayerCommandPreprocessListener;
import de.soulhive.system.combat.listeners.PlayerDeathListener;
import de.soulhive.system.combat.listeners.PlayerQuitListener;
import de.soulhive.system.combat.task.CombatUpdateTask;
import de.soulhive.system.service.FeatureService;
import de.soulhive.system.service.Service;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.task.TaskService;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.*;
import java.util.stream.Collectors;

@FeatureService
public class CombatService extends Service {

    private static final long COMBAT_TIME = 8000;
    public static final String[] ALLOWED_COMMANDS = {
        "msg", "report", "vote", "buy", "teamchat", "tc", "spec", "spectator", "stats", "friede"
    };

    private Set<Listener> listeners;
    private Map<Player, Long> fightTimestamps = new HashMap<>();
    private TaskService taskService = SoulHive.getServiceManager().getService(TaskService.class);

    @Override
    public void initialize() {
        this.listeners = new HashSet<>();

        this.listeners.add(new EntityDamageByEntityListener(this));
        this.listeners.add(new PlayerCommandPreprocessListener(this));
        this.listeners.add(new PlayerDeathListener(this));
        this.listeners.add(new PlayerQuitListener(this));

        this.taskService.registerTasks(
            new CombatUpdateTask(this)
        );
    }

    public void setFighting(Player player) {
        this.fightTimestamps.put(player, System.currentTimeMillis() + COMBAT_TIME);
    }

    public void detachFight(Player player, boolean intentional) {
        if (!this.fightTimestamps.containsKey(player)) {
            return;
        }

        this.fightTimestamps.remove(player);

        if (intentional) {
            player.setHealth(0);
            player.teleport(Settings.LOCATION_SPAWN);
        }
    }

    public boolean isFighting(Player player) {
        return this.getPlayersInFight().contains(player) && this.isUpdated(this.fightTimestamps.get(player));
    }

    public boolean isUpdated(long timeStamp) {
        return timeStamp > System.currentTimeMillis();
    }

    public boolean isCommandAllowed(String command) {
        return Arrays
            .stream(ALLOWED_COMMANDS)
            .anyMatch(allowedCommand -> allowedCommand.startsWith(command.toLowerCase()));
    }

    public long getStartTimeStamp(Player player) {
        return this.fightTimestamps.get(player);
    }

    public List<Player> getPlayersInFight() {
        return this.fightTimestamps.keySet()
            .stream()
            .filter(OfflinePlayer::isOnline)
            .collect(Collectors.toList());
    }

    @Override
    public Set<Listener> getListeners() {
        return this.listeners;
    }

}
