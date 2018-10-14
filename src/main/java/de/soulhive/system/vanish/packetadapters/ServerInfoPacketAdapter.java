package de.soulhive.system.vanish.packetadapters;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ServerInfoPacketAdapter extends PacketAdapter {

    private static final PacketType PACKET_TYPE = PacketType.Status.Server.SERVER_INFO;

    private List<Player> vanishedPlayers;

    public ServerInfoPacketAdapter(JavaPlugin plugin, List<Player> vanishedPlayers) {
        super(plugin, PACKET_TYPE);

        this.vanishedPlayers = vanishedPlayers;
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        WrappedServerPing ping = event.getPacket().getServerPings().read(0);
        int diff = Bukkit.getOnlinePlayers().size() - (int) this.vanishedPlayers
            .stream()
            .filter(Player::isOnline)
            .count();
        StringBuilder stringBuilder = new StringBuilder("§c\n§c\n");

        if (diff < 1) {
            stringBuilder.append("\n").append("§cAktuell sind keine Spieler online.");
        }

        Bukkit.getOnlinePlayers()
            .stream()
            .filter(player -> !this.vanishedPlayers.contains(player))
            .forEach(player -> stringBuilder.append("\n").append("§f").append(player.getName()));

        stringBuilder.append("§c\n§c\n§c\n");

        ping.setPlayers(Collections.singletonList(new WrappedGameProfile(UUID.randomUUID(), stringBuilder.toString())));
        ping.setPlayersOnline(diff);
    }

}
