package de.soulhive.system.rank;

import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;

@Getter
@ToString
public enum PremiumRank {

    GOLD("gold", 10000, Material.GOLD_INGOT, "§6", "§8► §6Kit Gold", "§8► §6Bis zu 5 Inselmitglieder", "§8► §6/repair", "§8► §6/feed", "§8► §6/heal", "§8► §6/tpa", "§8► §6/tpahere"),

    DIAMOND("diamond", 20000, Material.DIAMOND, "§b", "§8► §7Rechte von §6Gold", "§8► §bBis zu 6 Inselmitglieder", "§8► §bKit Diamond", "§8► §b/stack", "§8► §b/workbench", "§8► §b/anvil", "§8► §b/cook"),

    EMERALD("emerald", 30000, Material.EMERALD, "§a", "§8► §7Rechte von §6Gold§7, §bDiamond", "§8► §aBis zu 7 Inselmitglieder", "§8► §aKit Emerald", "§8► §a/invsee", "§8► §a/enderchest", "§8► §a/bottle", "§8► §a/fill"),

    OBSIDIAN("obsidian", 50000, Material.OBSIDIAN, "§5", "§8► §7Rechte von §6Gold§7, §bDiamond§7, §aEmerald", "§8► §5Bis zu 8 Inselmitglieder", "§8► §5Kein Levelverlust beim Tod", "§8► §5Kit Obsidian", "§8► §5/fly", "§8► §5/hat", "§8► §5/skull", "§8► §5/trash", "§8► §5/enchanter", "§8► §5/bodysee");

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
