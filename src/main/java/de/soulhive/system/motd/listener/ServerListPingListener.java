package de.soulhive.system.motd.listener;

import de.soulhive.system.motd.MotdService;
import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

@AllArgsConstructor
public class ServerListPingListener implements Listener {

    private MotdService motdService;

    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
        event.setMotd(
            this.motdService.fetchHeader() + "\n" + this.motdService.fetchFooter()
        );
    }

}
