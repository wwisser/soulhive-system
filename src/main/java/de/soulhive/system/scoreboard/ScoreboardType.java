package de.soulhive.system.scoreboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;

@AllArgsConstructor
@Getter
public enum ScoreboardType {

    USER("§2User", Material.FEATHER, 0),
    ADMIN("§cAdmin", Material.TNT, 1000),
    PVP("§6PvP", Material.STONE_SWORD, 1500);

    private String name;
    private Material material;
    private int costs;

    public String getPermission() {
        return "soulhive.scoreboard." + this.toString().toLowerCase();
    }

}
