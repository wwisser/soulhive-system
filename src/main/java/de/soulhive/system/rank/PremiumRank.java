package de.soulhive.system.rank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;

@AllArgsConstructor
@Getter
@ToString
public enum PremiumRank {

    GOLD("gold", 10000, Material.GOLD_INGOT, "§6"),
    DIAMOND("diamond", 20000, Material.DIAMOND, "§b"),
    EMERALD("emerald", 30000, Material.EMERALD, "§a"),
    OBSIDIAN("obsidian", 50000, Material.OBSIDIAN, "§5");

    private String groupName;
    private int costs;
    private Material material;
    private String chatColor;

    public String getName() {
        return StringUtils.capitalize(this.groupName);
    }

    public String getPermission() {
        return "soulhive." + this.groupName;
    }

}
