package de.soulhive.system.rank;

import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

@Getter
@ToString
public enum PremiumRank {

    GOLD("gold", 10000, Material.GOLD_INGOT, "§6", "§8► §6Kit Gold", "§8► §6Bis zu 5 Inselmitglieder", "§8► §6/repair", "§8► §6/feed", "§8► §6/heal", "§8► §6/tpa", "§8► §6/tpahere"),

    DIAMOND("diamond", 20000, Material.DIAMOND, "§b", "§8► §7Rechte von §6Gold", "§8► §bBis zu 6 Inselmitglieder", "§8► §bKit Diamond", "§8► §bFarbig auf Schildern schreiben", "§8► §b/stack", "§8► §b/workbench", "§8► §b/anvil", "§8► §b/cook"),

    EMERALD("emerald", 30000, Material.EMERALD, "§a", "§8► §7Rechte von §6Gold§7, §bDiamond", "§8► §aBis zu 7 Inselmitglieder", "§8► §aKit Emerald", "§8► §a/invsee", "§8► §a/enderchest", "§8► §a/bottle", "§8► §a/fill", "§8► §a/top"),

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

    public static PremiumRank getRank(final Player player) {
        for (int i = PremiumRank.values().length - 1; i >= 0; i--) {
            final PremiumRank rank = PremiumRank.values()[i];

            if (player.hasPermission(rank.getPermission())) {
                return rank;
            }
        }

        return null;
    }

    public static int getCurrentCosts(final Player player, final PremiumRank premiumRank) {
        if (player.hasPermission("soulhive.obsidian")) {
            return 0;
        }

        final PremiumRank rank = PremiumRank.getRank(player);

        if (rank == null) {
            return premiumRank.costs;
        }

        if (premiumRank.getCosts() <= rank.getCosts()) {
            return 0;
        }

        return premiumRank.getCosts() - rank.getCosts();
    }

}
