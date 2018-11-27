package de.soulhive.system.vanish;

import de.soulhive.system.service.FeatureService;
import de.soulhive.system.service.Service;
import de.soulhive.system.vanish.commands.CommandVanish;
import de.soulhive.system.vanish.listeners.PlayerJoinListener;
import de.soulhive.system.vanish.packetadapters.ServerInfoPacketAdapter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

@FeatureService
@RequiredArgsConstructor
public class VanishService extends Service {

    private final JavaPlugin plugin;
    @Getter private List<Player> vanishedPlayers = new ArrayList<>();

    @Override
    public void initialize() {
        super.registerPacketAdapter(new ServerInfoPacketAdapter(this.plugin, this));
        super.registerListener(new PlayerJoinListener(this));
        super.registerCommand("vanish", new CommandVanish(this));
    }

    public int getOnlinePlayersDiff() {
        return Bukkit.getOnlinePlayers().size() - (int) this.vanishedPlayers
            .stream()
            .filter(Player::isOnline)
            .count();
    }

    @Override
    public void disable() {
        for (Player vanishedPlayer : this.vanishedPlayers) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.showPlayer(vanishedPlayer);
            }
        }
    }

}
