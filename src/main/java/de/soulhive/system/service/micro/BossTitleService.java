package de.soulhive.system.service.micro;

import de.soulhive.system.SoulHive;
import de.soulhive.system.service.Service;
import de.soulhive.system.task.ComplexTask;
import de.soulhive.system.task.TaskService;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class BossTitleService extends Service {

    private Map<String, EntityWither> withers = new HashMap<>();

    @Override
    public void initialize() {
        SoulHive.getServiceManager().getService(TaskService.class).registerTasks(new BossTitleUpdateTask());
    }

    @Override
    public void disable() {
        Bukkit.getOnlinePlayers().forEach(this::removePlayer);
    }

    private class BossTitleUpdateTask extends BukkitRunnable implements ComplexTask {

        @Override
        public void setup(JavaPlugin plugin) {
            super.runTaskTimer(plugin, 10L, 5L);
        }

        @Override
        public void run() {
            BossTitleService.this.sendBossBar();
        }

    }

    private void sendBossBar() {
        for (final Map.Entry<String, EntityWither> entry : this.withers.entrySet()) {
            final EntityWither wither = entry.getValue();
            final Player player = Bukkit.getPlayer(entry.getKey());
            if (player == null) {
                this.withers.remove(entry.getKey());
                continue;
            }
            final Location location = this.getWitherLocation(player.getLocation());
            wither.setLocation(location.getX(), location.getY(), location.getZ(), 0, 0);
            final PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport(wither);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        }
    }

    public void addPlayer(final Player player, final String title) {
        final EntityWither wither = new EntityWither(((CraftWorld) player.getWorld()).getHandle());
        final Location l = this.getWitherLocation(player.getLocation());
        wither.setCustomName(title);
        wither.setInvisible(true);
        wither.setLocation(l.getX(), l.getY(), l.getZ(), 0, 0);
        final PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(wither);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        this.withers.put(player.getName(), wither);
    }

    public void removePlayer(final Player player) {
        final EntityWither wither = this.withers.remove(player.getName());
        if (wither == null) return;
        final PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(wither.getId());
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    private Location getWitherLocation(final Location location) {
        return location.add(location.getDirection().multiply(60));
    }

}
