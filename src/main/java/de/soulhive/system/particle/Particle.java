package de.soulhive.system.particle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Material;

@AllArgsConstructor
@Getter
public enum Particle {

    HAPPY_VILLAGER("§a§lHappy Villager", EnumParticle.VILLAGER_HAPPY, Material.EMERALD, 500),
    ANGRY_VILLAGER("§c§lAngry Villager", EnumParticle.VILLAGER_ANGRY, Material.BLAZE_POWDER, 500),
    FIREWORK_SPARK("§f§lFirework Spark", EnumParticle.FIREWORKS_SPARK, Material.FIREWORK, 500);

    private String name;
    private EnumParticle enumParticle;
    private Material material;
    private int costs;

}
