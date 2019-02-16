package de.soulhive.system.npc.impl;

import de.soulhive.system.npc.HologramNpc;
import lombok.SneakyThrows;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftZombie;
import org.bukkit.entity.Player;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ZombieHologramNpc extends EntityZombie implements HologramNpc {

    private static final int ENTITY_TYPE_ID = 54;
    private static final String ENTITY_NAME = "Zombie";

    private static final double HOLOGRAM_HEIGHT = 2.35;

    private Location location;
    private BlockFace blockFace;
    private Consumer<Player> clickAction;
    private BiConsumer<Player, CraftEntity> damageAction;
    private String hologramName;

    public ZombieHologramNpc(
        final Location location,
        final BlockFace blockFace,
        final Consumer<Player> clickAction,
        final BiConsumer<Player, CraftEntity> damageAction,
        final String hologramName
    ) {
        super(((CraftWorld) location.getWorld()).getHandle());
        this.location = location;
        this.blockFace = blockFace;
        this.clickAction = clickAction;
        this.hologramName = hologramName;
        this.damageAction = damageAction;
        super.ai = false;

        super.setPosition(location.getX(), location.getY(), location.getZ());
        super.setYawPitch(location.getYaw(), location.getPitch());
    }

    @Override
    public void g(final double a, final double b, final double c) { }

    @Override
    public boolean a(EntityHuman entityhuman) {
        return false;
    }

    @Override
    public boolean damageEntity(final DamageSource damageSource, final float f) {
        if (damageSource.getEntity() == null) {
            return false;
        }

        final CraftEntity bukkitEntity = damageSource.getEntity().getBukkitEntity();

        if (bukkitEntity instanceof CraftPlayer) {
            this.damageAction.accept(((CraftPlayer) bukkitEntity), super.getBukkitEntity());
        }

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
    public BlockFace getBlockFace() {
        return this.blockFace;
    }

    @Override
    public Entity getEntity() {
        return this;
    }

    @Override
    public String getEntityName() {
        return ENTITY_NAME;
    }

    @Override
    public String getHologramName() {
        return this.hologramName;
    }

    @Override
    public double getHeight() {
        return HOLOGRAM_HEIGHT;
    }

    @Override
    protected String z() {
        return "";
    }


}
