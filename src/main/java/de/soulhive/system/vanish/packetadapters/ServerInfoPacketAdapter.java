package de.soulhive.system.vanish.packetadapters;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import de.soulhive.system.vanish.VanishService;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.UUID;

public class ServerInfoPacketAdapter extends PacketAdapter {

    private static final PacketType PACKET_TYPE = PacketType.Status.Server.SERVER_INFO;

    private VanishService vanishService;

    public ServerInfoPacketAdapter(JavaPlugin plugin, VanishService vanishService) {
        super(plugin, PACKET_TYPE);

        this.vanishService = vanishService;
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        WrappedServerPing ping = event.getPacket().getServerPings().read(0);
        StringBuilder stringBuilder = new StringBuilder("§c\n");
        int diff = this.vanishService.getOnlinePlayersDiff();

        if (diff < 1) {
            stringBuilder.append("\n").append("§c Aktuell sind keine Spieler online. ");
        }

        Bukkit.getOnlinePlayers()
            .stream()
            .filter(player -> !this.vanishService.getVanishedPlayers().contains(player))
            .forEach(player -> stringBuilder.append("\n").append("§f").append(player.getName()));

        stringBuilder.append("§c\n§c\n");

        ping.setPlayers(Collections.singletonList(
            new WrappedGameProfile(UUID.randomUUID(), " "+ stringBuilder.toString() + " ")
        ));
        ping.setPlayersOnline(diff);
    }

}
