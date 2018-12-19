package de.soulhive.system.entity;

import lombok.SneakyThrows;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;

import java.lang.reflect.Field;
import java.util.Map;

public class ImmutableVillager extends EntityVillager {

    public ImmutableVillager(Location location) {
        super(((CraftWorld) location.getWorld()).getHandle());

        ((Map<Class<? extends Entity>, String>) ImmutableVillager.getObject(net.minecraft.server.v1_8_R3.EntityTypes.class, "d", null)).put(this.getClass(), "Villager");
        ((Map<Class<? extends net.minecraft.server.v1_8_R3.Entity>, Integer>) ImmutableVillager.getObject(net.minecraft.server.v1_8_R3.EntityTypes.class, "f", null)).put(this.getClass(), 120);

        ((UnsafeList<PathfinderGoal>) ImmutableVillager.getObject(PathfinderGoalSelector.class, "b", super.goalSelector)).clear();
        ((UnsafeList<PathfinderGoal>) ImmutableVillager.getObject(PathfinderGoalSelector.class, "c", super.goalSelector)).clear();

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

    @SneakyThrows
    private static Object getObject(final Class<?> target, final String variable, final Object object) {
        final Field field = target.getDeclaredField(variable);
        field.setAccessible(true);

        return field.get(object);
    }

}
