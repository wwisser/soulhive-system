package de.soulhive.system.scoreboard;

import lombok.Getter;
import org.bukkit.Material;

@Getter
public enum ScoreboardType {

    USER("§2User", Material.FEATHER, 0, "§8- §7Spieler", "§8- §7Kills", "§8- §7Juwelen"),
    ADMIN("§cAdmin", Material.TNT, 300, "§8- §7Spieler", "§8- §7TPS (Ticks/second)", "§8- §7Ping"),
    PVP("§6PvP", Material.STONE_SWORD, 500, "§8- §7Kills", "§8- §7Deaths", "§8- §7KD/r"),
    ISLAND("§bIsland", Material.GRASS, 600, "§8- §7Name", "§8- §7Level", "§8- §7Mitglieder");

    private String name;
    private Material material;
    private int costs;
    private String[] properties;

    ScoreboardType(final String name, final Material material, final int costs, final String... properties) {
        this.name = name;
        this.material = material;
        this.costs = costs;
        this.properties = properties;
    }

    public String getPermission() {
        return "soulhive.scoreboard." + this.toString().toLowerCase();
    }

}
