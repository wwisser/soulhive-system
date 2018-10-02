package de.soulhive.system.util.nms;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@UtilityClass
public class Tablist {

    private List<String> SUPPORTED_VERSIONS = Arrays.asList("V1_8_R2", "V1_8_R3");

    @SneakyThrows
    public void send(Player player, String head, String foot) {
        if (SUPPORTED_VERSIONS.contains(Tablist.getServerVersion().toUpperCase())) {
            Object header = Tablist
                .getNmsClass("IChatBaseComponent$ChatSerializer")
                .getMethod("a", String.class)
                .invoke(null, "{'text': '" + head + "'}");
            Object footer = Tablist
                .getNmsClass("IChatBaseComponent$ChatSerializer")
                .getMethod("a", String.class)
                .invoke(null, "{'text': '" + foot + "'}");

            Object ppoplhf = Tablist.getNmsClass("PacketPlayOutPlayerListHeaderFooter")
                .getConstructor(new Class[]{getNmsClass("IChatBaseComponent")})
                .newInstance(header);

            Field field = ppoplhf.getClass().getDeclaredField("b");
            field.setAccessible(true);
            field.set(ppoplhf, footer);

            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);

            playerConnection.getClass().getMethod(
                "sendPacket", getNmsClass("Packet")
            ).invoke(playerConnection, ppoplhf);
        } else {
            Object header = Tablist
                .getNmsClass("ChatSerializer")
                .getMethod("a", String.class)
                .invoke(null, "{'text': '" + head + "'}");
            Object footer = Tablist
                .getNmsClass("ChatSerializer")
                .getMethod("a", String.class)
                .invoke(null, "{'text': '" + foot + "'}");

            Object packet = Tablist
                .getNmsClass("PacketPlayOutPlayerListHeaderFooter")
                .getConstructor(new Class[]{getNmsClass("IChatBaseComponent")})
                .newInstance(header);

            Field field = packet.getClass().getDeclaredField("b");
            field.setAccessible(true);
            field.set(packet, footer);

            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);

            playerConnection.getClass().getMethod(
                "sendPacket", getNmsClass("Packet")
            ).invoke(playerConnection, packet);
        }
    }

    @SneakyThrows
    private Class<?> getNmsClass(String className) {
        return Class.forName(
            "net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName()
                .replace(".", ",").split(",")[3] + "." + className
        );
    }

    private String getServerVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().substring(23);
    }

}
