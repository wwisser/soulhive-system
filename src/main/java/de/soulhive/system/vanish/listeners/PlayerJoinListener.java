package de.soulhive.system.vanish.listeners;

import de.soulhive.system.setting.Settings;
import de.soulhive.system.vanish.VanishService;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

@AllArgsConstructor
public class PlayerJoinListener implements Listener {

    private VanishService vanishService;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        List<Player> vanishedPlayers = this.vanishService.getVanishedPlayers();

        if (!player.hasPermission(Settings.PERMISSION_TEAM)) {
            vanishedPlayers.forEach(player::hidePlayer);
        }

        if (vanishedPlayers.contains(player)) {
            player.sendMessage(
                Settings.PREFIX
                    + "Du bist gerade gevanished. Benutze §f/vanish §7um den Modus zu deaktivieren."
            );
        }
    }

}
