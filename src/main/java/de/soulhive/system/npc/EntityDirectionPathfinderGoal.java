package de.soulhive.system.npc;

import lombok.AllArgsConstructor;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.PathfinderGoal;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;

@AllArgsConstructor
public class EntityDirectionPathfinderGoal extends PathfinderGoal {

    private EntityInsentient e;
    private BlockFace bf;

    @Override
    public boolean a() {
        return true;
    }

    @Override
    public void e() {
        final Location location = new Location(
            this.e.world.getWorld(),
            this.e.locX,
            this.e.locY,
            this.e.locZ
        ).getBlock().getRelative(this.bf).getLocation();

        this.e.getControllerLook().a(
            location.getBlockX(),
            this.e.locY + this.e.getHeadHeight(),
            location.getBlockZ(),
            10F,
            this.e.bQ()
        );
    }

}