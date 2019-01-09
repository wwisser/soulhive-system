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
    private static final String ENTITY_NAME = "Villager";

    private Location location;
    private Consumer<Player> clickAction;

    public VillagerNpc(final Location location, final Consumer<Player> clickAction) {
        super(((CraftWorld) location.getWorld()).getHandle());
        this.location = location;
        this.clickAction = clickAction;
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
        return this.clickAction;
    }

    @Override
    public int getEntityTypeId() {
        return ENTITY_TYPE_ID;
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    @Override
    public Entity getEntity() {
        return this;
    }

    @Override
    public String getEntityName() {
        return ENTITY_NAME;
    }

}
