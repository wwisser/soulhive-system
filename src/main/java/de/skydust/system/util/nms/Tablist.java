package de.skydust.system.util.nms;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

@UtilityClass
public class Tablist {

    @SneakyThrows
    public void send(Player player, String head, String foot) {
        if ((getServerVersion().equalsIgnoreCase("v1_8_R2")) ||
                (getServerVersion().equalsIgnoreCase("v1_8_R3"))) {
            Object header = getNmsClass("IChatBaseComponent$ChatSerializer")
                    .getMethod("a", String.class)
                    .invoke(null, "{'text': '" + head + "'}");
            Object footer = getNmsClass("IChatBaseComponent$ChatSerializer")
                    .getMethod("a", String.class)
                    .invoke(null, "{'text': '" + foot + "'}");

            Object ppoplhf = getNmsClass("PacketPlayOutPlayerListHeaderFooter")
                    .getConstructor(new Class[]{getNmsClass("IChatBaseComponent")})
                    .newInstance(header);

            Field field = ppoplhf.getClass().getDeclaredField("b");
            field.setAccessible(true);
            field.set(ppoplhf, footer);

            Object nmsp = player.getClass().getMethod("getHandle")
                    .invoke(player);
            Object pcon = nmsp.getClass().getField("playerConnection").get(nmsp);

            pcon.getClass().getMethod("sendPacket", getNmsClass("Packet"))
                    .invoke(pcon, ppoplhf);
        } else {
            Object header = getNmsClass("ChatSerializer")
                    .getMethod("a", String.class)
                    .invoke(null, "{'text': '" + head + "'}");
            Object footer = getNmsClass("ChatSerializer")
                    .getMethod("a", String.class)
                    .invoke(null, "{'text': '" + foot + "'}");

            Object ppoplhf = getNmsClass("PacketPlayOutPlayerListHeaderFooter")
                    .getConstructor(new Class[]{getNmsClass("IChatBaseComponent")})
                    .newInstance(header);

            Field f = ppoplhf.getClass().getDeclaredField("b");
            f.setAccessible(true);
            f.set(ppoplhf, footer);

            Object nmsp = player.getClass().getMethod("getHandle")
                    .invoke(player);
            Object pcon = nmsp.getClass().getField("playerConnection").get(nmsp);

            pcon.getClass().getMethod("sendPacket", getNmsClass("Packet"))
                    .invoke(pcon, ppoplhf);
        }
    }

    @SneakyThrows
    private Class<?> getNmsClass(String nmsClassName) {
        return Class.forName(
                "net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName()
                        .replace(".", ",").split(",")[3] + "." + nmsClassName);
    }

    private String getServerVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().substring(23);
    }

}
