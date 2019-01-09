package de.soulhive.system.npc.impl;

import de.soulhive.system.npc.Npc;
import lombok.SneakyThrows;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.function.Consumer;

public class VillagerNpc extends EntityVillager implements Npc {

    private static final int ENTITY_TYPE_ID = 120;

    public VillagerNpc(Location location) {
        super(((CraftWorld) location.getWorld()).getHandle());

        ((Map<Class<? extends Entity>, String>) VillagerNpc.getObject(net.minecraft.server.v1_8_R3.EntityTypes.class, "d", null)).put(this.getClass(), "Villager");
        ((Map<Class<? extends net.minecraft.server.v1_8_R3.Entity>, Integer>) VillagerNpc.getObject(net.minecraft.server.v1_8_R3.EntityTypes.class, "f", null)).put(this.getClass(), 120);

        ((UnsafeList<PathfinderGoal>) VillagerNpc.getObject(PathfinderGoalSelector.class, "b", super.goalSelector)).clear();
        ((UnsafeList<PathfinderGoal>) VillagerNpc.getObject(PathfinderGoalSelector.class, "c", super.goalSelector)).clear();

        super.goalSelector.a(0, new PathfinderGoalFloat(this));
        super.setPosition(location.getX(), location.getY(), location.getZ());

        super.world.addEntity(this);
    }

    @Override
    public void g(final double a, final double b, final double c) { }

    @Override
    public boolean a(EntityHuman entityhuman) {
        return false;
    }

    @Override
    public boolean damageEntity(final DamageSource damageSource, final float f) {
        return false;
    }

    @Override
    public Consumer<Player> getClickAction() {
        return player -> { player.sendMessage("It works"); };
    }

    @Override
    public int getEntityTypeId() {
        return ENTITY_TYPE_ID;
    }

    @Override
    public Entity getEntity() {
        return this;
    }

}
