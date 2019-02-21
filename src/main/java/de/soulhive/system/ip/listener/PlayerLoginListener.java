package de.soulhive.system.ip.listener;

import de.soulhive.system.ip.IpResolverService;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Set;

@AllArgsConstructor
public class PlayerLoginListener implements Listener {

    private IpResolverService ipResolverService;

    @EventHandler
    public void onPlayerLogin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final String ip = player.getAddress().toString().split(":")[0].replace(".", "*").replace("/", "");
        final String labelIps = "ips." + player.getUniqueId().toString();
        final String labelPlayers = "players." + ip;

        final Set<String> ips = this.ipResolverService.getEntries(labelIps);
        ips.add(ip);

        final Set<String> players = this.ipResolverService.getEntries(labelPlayers);
        players.add(player.getUniqueId().toString());

        this.ipResolverService.updateEntries(labelIps, ips);
        this.ipResolverService.updateEntries(labelPlayers, players);
    }

}
