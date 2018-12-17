package de.soulhive.system.service.micro;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import de.soulhive.system.SoulHive;
import de.soulhive.system.service.Service;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class PayloadPreventionService extends Service {

    @Override
    public void initialize() {
        super.registerPacketAdapter(new CustomPayloadPacketAdapter());
    }

    private class CustomPayloadPacketAdapter extends PacketAdapter {

        CustomPayloadPacketAdapter() {
            super(SoulHive.getPlugin(), ListenerPriority.NORMAL, PacketType.Play.Client.CUSTOM_PAYLOAD);
        }

        @Override
        public void onPacketReceiving(PacketEvent packetEvent) {
            Player player = packetEvent.getPlayer();
            String packetName = packetEvent.getPacket().getStrings().readSafely(0);

            if (Arrays.asList("MC|BEdit", "MC|BSign").contains(packetName)) {
                if (player.getItemInHand() != null) {
                    if (!player.getItemInHand().getType().equals(Material.BOOK_AND_QUILL)) {
                        packetEvent.setCancelled(true);
                    }
                } else {
                    packetEvent.setCancelled(true);
                }
            }

            if (Arrays.asList("WDL|INIT", "WDL|CONTROL").contains(packetName)) {
                Bukkit.dispatchCommand(
                    Bukkit.getConsoleSender(),
                    "banip " + player.getName() + " WorldDownloader ist verboten"
                );
            }
        }
    }

}
