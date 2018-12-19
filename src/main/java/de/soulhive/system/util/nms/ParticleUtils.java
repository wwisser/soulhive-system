package de.soulhive.system.util.nms;

import lombok.experimental.UtilityClass;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

@UtilityClass
public class ParticleUtils {

    public void play(final Player player,
                     final Location location,
                     final EnumParticle particle,
                     final float xoff,
                     final float zoff,
                     final float yoff,
                     final float speed,
                     final int amount
    ) {
        final float x = (float) location.getX(), y = (float) location.getY(), z = (float) location.getZ();

        final PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
            particle, true, x, y, z, xoff, yoff, zoff, speed, amount
        );
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    public void play(
        final Location location,
        final EnumParticle particle,
        final float xoff,
        final float zoff,
        final float yoff,
        final float speed,
        final int amount
    ) {
        Bukkit.getOnlinePlayers().forEach(
            player -> ParticleUtils.play(player, location, particle, xoff, zoff, yoff, speed, amount)
        );
    }

}
