package de.soulhive.system.vanish;

import com.comphenix.protocol.events.PacketAdapter;
import de.soulhive.system.service.Service;
import de.soulhive.system.vanish.commands.CommandVanish;
import de.soulhive.system.vanish.listeners.PlayerJoinListener;
import de.soulhive.system.vanish.packetadapters.ServerInfoPacketAdapter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

@RequiredArgsConstructor
public class VanishService extends Service {

    private final JavaPlugin plugin;
    @Getter private List<Player> vanishedPlayers = new ArrayList<>();

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

    @Override
    public Set<Listener> getListeners() {
        return Collections.singleton(
            new PlayerJoinListener(this)
        );
    }

    @Override
    public Map<String, CommandExecutor> getCommands() {
        return Collections.singletonMap(
            "vanish", new CommandVanish(this)
        );
    }

    @Override
    public Set<PacketAdapter> getPacketAdapters() {
        return Collections.singleton(
            new ServerInfoPacketAdapter(this.plugin, this)
        );
    }

}
