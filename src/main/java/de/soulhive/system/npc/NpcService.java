package de.soulhive.system.npc;

import de.soulhive.system.service.FeatureService;
import de.soulhive.system.service.Service;
import de.soulhive.system.util.ReflectUtils;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@FeatureService
public class NpcService extends Service {

    private List<Npc> npcs = new ArrayList<>();
    private Map<HologramNpc, Hologram>

    @Override
    public void disable() {
        this.npcs.forEach(npc -> npc.getEntity().die());
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
        }

        final Location location = npc.getLocation();

        npc.getEntity().setPosition(location.getX(), location.getY(), location.getZ());
        npc.getEntity().world.addEntity(npc.getEntity());
    }

    private boolean isRegistered(final Npc npc) {
        return this.npcs
            .stream()
            .map(Npc::getClass)
            .anyMatch(npcClass -> npcClass.equals(npc.getClass()));
    }

}
