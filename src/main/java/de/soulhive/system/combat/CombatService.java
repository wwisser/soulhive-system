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

import java.util.*;
import java.util.stream.Collectors;

@FeatureService
public class CombatService extends Service {

    private static final long COMBAT_TIME = 8000;
    private static final String[] ALLOWED_COMMANDS = {
        "/msg", "/report", "/vote", "/buy", "/teamchat", "/tc",
        "/spec", "/spectator", "/stats", "/friede", "/frieden", "/stack", "/kit"
    };
    private static final String[] INSTANT_TELEPORT_COMMANDS  = {
        "/is", "/tpa", "/tpaccept", "/tpahere",
    };

    private Map<Player, Long> fightTimestamps = new HashMap<>();

    @Override
    public void initialize() {
        TaskService taskService = SoulHive.getServiceManager().getService(TaskService.class);

        super.registerListeners(
            new EntityDamageByEntityListener(this),
            new PlayerCommandPreprocessListener(this),
            new PlayerDeathListener(this),
            new PlayerQuitListener(this)
        );

        taskService.registerTasks(
            new CombatUpdateTask(this)
        );
    }

    public void setFighting(Player player) {
        if (player.hasPermission("soulhive.combatlog.bypass")) {
            return;
        }

        if (!this.isFighting(player)) {
            player.sendMessage(Settings.PREFIX + "§cDu bist jetzt im Kampf, bitte logge dich §c§nnicht§c aus.");
        }
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

    public boolean isCommandTeleportable(String command) {
        return Arrays
            .stream(INSTANT_TELEPORT_COMMANDS)
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

}
