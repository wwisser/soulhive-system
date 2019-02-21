package de.soulhive.system.ip;

import de.soulhive.system.ip.command.CommandAlt;
import de.soulhive.system.service.FeatureService;
import de.soulhive.system.service.Service;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.util.Config;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@FeatureService
public class IpResolverService extends Service implements Listener {

    private Config database = new Config(Settings.CONFIG_PATH, "ips.yml");

    @Override
    public void initialize() {
        super.registerListener(this);
        super.registerCommand("alt", new CommandAlt(this));
    }

    @EventHandler
    public void onPlayerLogin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final String ip = player.getAddress().toString().split(":")[0].replace(".", "*").replace("/", "");
        final String labelIps = "ips." + player.getUniqueId().toString();
        final String labelPlayers = "players." + ip;

        final Set<String> ips = this.getEntries(labelIps);
        ips.add(ip);

        final Set<String> players = this.getEntries(labelPlayers);
        players.add(player.getUniqueId().toString());

        this.updateEntries(labelIps, ips);
        this.updateEntries(labelPlayers, players);
    }

    public Set<String> getAlternativeIps(final String uuid) {
        return this.getEntries("ips." + uuid);
    }

    /**
     * @return one of the UUIDs could be the same as given IP owner
     */
    public Set<String> getAlternativeAccounts(final String ip) {
        return this.getEntries("players." + ip);
    }

    private Set<String> getEntries(final String path) {
        Set<String> entries;

        if (this.database.contains(path)) {
            entries = new HashSet<>(this.database.getStringList(path));
        } else {
            entries = new HashSet<>();
        }

        return entries;
    }

    private void updateEntries(final String path, final Set<String> entries) {
        this.database.set(path, new ArrayList<>(entries));
        this.database.saveFile();
    }

}
