package de.soulhive.system.scoreboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;

@AllArgsConstructor
@Getter
public enum ScoreboardType {

    USER("§2User", Material.FEATHER),
    PVP("§6PvP", Material.STONE_SWORD),
    ADMIN("§cAdmin", Material.TNT);

    private String name;
    private Material material;

    public String getPermission() {
        return "soulhive." + this.toString().toLowerCase();
    }

    public static ScoreboardType fromMaterial(final Material material) {
        for (ScoreboardType type : ScoreboardType.values()) {
            if (type.getMaterial() == material) {
                return type;
            }
        }

        return null;
    }

}
