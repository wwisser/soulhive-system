package de.soulhive.system.setting;

import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Settings {

    public static final String PREFIX = "§9§lSoulHive §8➥ §7";

    public static final String COMMAND_NO_PERMISSION = "§cDu hast keinen Zugriff auf diesen Befehl.";
    public static final String COMMAND_ONLY_PLAYERS = "Diesen Befehl dürfen nur Spieler verwenden.";
    public static final String COMMAND_USAGE = "§cVerwendung: §f";

    public static final String PERMISSION_ADMIN = "soulhive.admin";
    public static final String PERMISSION_TEAM = "soulhive.team";
    public static final String PERMISSION_BUILD = "soulhive.build";

    public static final String CONFIG_PATH = "plugins/SoulHive-System";

    public static final World WORLD_MAIN = Bukkit.getWorld("SoulHive");
    public static final World WORLD_SKYBLOCK = Bukkit.getWorld("ASkyBlock");
    private static final World WORLD_SKYBLOCK_NETHER = Bukkit.getWorld("ASkyBlock_nether");

    public static final Location LOCATION_SPAWN = new Location(WORLD_MAIN, -56.5, 183, -351.5, 0, 0);

    public static final List<World> SKYBLOCK_WORLDS = ImmutableList.of(WORLD_SKYBLOCK, WORLD_SKYBLOCK_NETHER);

    public static final int SPAWN_HEIGHT = WORLD_MAIN.getSpawnLocation().getBlockY() - 20;

}
