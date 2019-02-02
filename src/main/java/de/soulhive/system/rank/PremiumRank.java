package de.soulhive.system.rank;

import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;

@Getter
@ToString
public enum PremiumRank {

    // obsidian: fly (unique), hat, skull, trash, enchanter, bodysee
    // emerald: invsee (unique), enderchest, bottle, fill
    // diamond: stack (unique), workbench, cook
    // gold: repair, feed, heal, tpa, /tpahere

    GOLD("gold", 10000, Material.GOLD_INGOT, "§6", "§8► §6/repair", "§8► §6/feed", "§8► §6/heal", "§8► §6/tpa, /tpahere"),

    DIAMOND("diamond", 20000, Material.DIAMOND, "§b"),

    EMERALD("emerald", 30000, Material.EMERALD, "§a"),

    OBSIDIAN("obsidian", 50000, Material.OBSIDIAN, "§5");

    public static final String[] PERMISSIONS_DEFAULT = {
        "§8► §7Prefix im Chat und Tab",
    };

    private String groupName;
    private int costs;
    private Material material;
    private String chatColor;
    private String[] permissions;

    PremiumRank(String groupName, int costs, Material material, String chatColor, String... permissions) {
        this.groupName = groupName;
        this.costs = costs;
        this.material = material;
        this.chatColor = chatColor;
        this.permissions = permissions;
    }

    public String getName() {
        return StringUtils.capitalize(this.groupName);
    }

    public String getPermission() {
        return "soulhive." + this.groupName;
    }

}
