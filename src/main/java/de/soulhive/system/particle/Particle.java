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
    NOTE("§5§lNote", EnumParticle.NOTE, Material.NOTE_BLOCK, 500),
    ENCHANTMENT_TABLE("§d§lEnchant", EnumParticle.ENCHANTMENT_TABLE, Material.ENCHANTMENT_TABLE, 500),
    HEART("§4§lHeart", EnumParticle.HEART, Material.APPLE, 500),
    SLIME("§a§lSlime", EnumParticle.SLIME, Material.SLIME_BALL, 500),
    FIREWORK_SPARK("§f§lFirework Spark", EnumParticle.FIREWORKS_SPARK, Material.FIREWORK, 500),
    SPELL_WITCH("§5§lWitch", EnumParticle.SPELL_WITCH, Material.SPIDER_EYE, 500),
    CRIT("§9§lCritical", EnumParticle.CRIT_MAGIC, Material.IRON_SWORD, 500);

    private String name;
    private EnumParticle enumParticle;
    private Material material;
    private int costs;

    public String getPermission() {
        return "soulhive.particle." + this.toString().toLowerCase().replace("-", "");
    }

}
