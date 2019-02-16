package de.soulhive.system.npc.impl;

import de.soulhive.system.npc.HologramNpc;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class VillagerHologramNpc extends EntityVillager implements HologramNpc {

    private static final int ENTITY_TYPE_ID = 120;
    private static final String ENTITY_NAME = "Villager";

    private static final double HOLOGRAM_HEIGHT = 2.35;

    private Location location;
    private BlockFace blockFace;
    private Consumer<Player> clickAction;
    private String hologramName;

    public VillagerHologramNpc(
        final Location location,
        final BlockFace blockFace,
        final Consumer<Player> clickAction,
        final String hologramName,
        final int profession
    ) {
        super(((CraftWorld) location.getWorld()).getHandle());
        this.location = location;
        this.blockFace = blockFace;
        this.clickAction = clickAction;
        this.hologramName = hologramName;
        super.ai = false;

        super.setPosition(location.getX(), location.getY(), location.getZ());
        super.setYawPitch(location.getYaw(), location.getPitch());

        super.setProfession(profession);
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
