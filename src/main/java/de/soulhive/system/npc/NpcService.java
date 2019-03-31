package de.soulhive.system.npc;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import de.soulhive.system.npc.listener.PlayerInteractAtEntityListener;
import de.soulhive.system.npc.listener.PlayerInteractEntityListener;
import de.soulhive.system.service.FeatureService;
import de.soulhive.system.service.Service;
import de.soulhive.system.util.ReflectUtils;
import jdk.nashorn.internal.ir.EmptyNode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftVillager;
import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@FeatureService
@RequiredArgsConstructor
public class NpcService extends Service {

    @Getter private List<Npc> npcs = new ArrayList<>();
    private List<Hologram> holograms = new ArrayList<>();

    @NonNull private JavaPlugin plugin;

    @Override
    public void initialize() {
        super.registerListeners(
            new PlayerInteractAtEntityListener(this),
            new PlayerInteractEntityListener(this)
        );
    }

    @Override
    public void disable() {
        this.npcs.forEach(npc -> npc.getEntity().die());
        this.holograms.forEach(Hologram::delete);
    }

    public boolean isNpc(final org.bukkit.entity.Entity entity) {
        return this.getNpcs()
            .stream()
            .anyMatch(npc -> npc.getEntity().getUniqueID().equals(entity.getUniqueId()));
    }

    public void addNpc(final Npc npc) {
        if (!this.isRegistered(npc)) {
            ((Map<Class<? extends Entity>, Integer>) ReflectUtils.getObject(
                EntityTypes.class,
                "f",
                null)
            ).put(
                (Class<? extends Entity>) npc.getClass(),
                npc.getEntityTypeId()
            );

            ((Map<Class<? extends Entity>, String>) ReflectUtils.getObject(EntityTypes.class, "d", null))
                .put(
                    (Class<? extends Entity>) npc.getClass(),
                    npc.getEntityName()
                );
        }

        if (npc.getEntity() instanceof EntityInsentient) {
            final EntityInsentient insentient = (EntityInsentient) npc.getEntity();

            ((UnsafeList<PathfinderGoal>) ReflectUtils.getObject(
                PathfinderGoalSelector.class, "b", insentient.goalSelector)
            ).clear();
            ((UnsafeList<PathfinderGoal>) ReflectUtils.getObject(
                PathfinderGoalSelector.class, "c", insentient.goalSelector)
            ).clear();

            insentient.goalSelector.a(0, new PathfinderGoalFloat(insentient));
            insentient.goalSelector.a(10, new EntityDirectionPathfinderGoal(insentient, npc.getBlockFace()));
        }

        final Location location = npc.getLocation();

        npc.getEntity().world.addEntity(npc.getEntity());
        npc.getEntity().setPosition(location.getX(), location.getY(), location.getZ());

        if (npc instanceof HologramNpc) {
            this.registerHologram(((HologramNpc) npc));
        }

        this.npcs.add(npc);
    }

    private void registerHologram(final HologramNpc hologramNpc) {
        final Hologram hologram = HologramsAPI.createHologram(
            this.plugin,
            hologramNpc.getLocation().clone().add(0, hologramNpc.getHeight(), 0)
        );

        hologram.appendTextLine(hologramNpc.getHologramName());
        this.holograms.add(hologram);
    }

    private boolean isRegistered(final Npc npc) {
        return this.npcs
            .stream()
            .map(Npc::getClass)
            .anyMatch(npcClass -> npcClass.equals(npc.getClass()));
    }

}
