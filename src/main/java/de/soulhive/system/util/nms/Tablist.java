package de.soulhive.system.util.nms;

import com.google.common.collect.ImmutableMap;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@UtilityClass
public class Tablist {

    public void send(Player player, String header, String footer) {
        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();

        ImmutableMap
            .of("a", header, "b", footer)
            .forEach((fieldName, line) -> {
                try {
                    Field field = packet.getClass().getDeclaredField(fieldName);

                    field.setAccessible(true);
                    field.set(packet, IChatBaseComponent.ChatSerializer
                        .a("{\"text\":\"" + ChatColor.translateAlternateColorCodes('&', line) + "\"}"));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

}
