package de.soulhive.system.setting;

import com.google.common.collect.ImmutableList;
import de.soulhive.system.npc.Npc;
import de.soulhive.system.npc.impl.VillagerHologramNpc;
import de.soulhive.system.util.item.ItemBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Settings {

    public static final String NAME = "§9§lSoulHive";
    public static final String PREFIX = NAME + " §8➥ §7";

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

    public static final Location LOCATION_SPAWN = new Location(WORLD_MAIN, -53.5, 183, -351.5, 0, 0);
    public static final Location LOCATION_ITEMSHOP = new Location(WORLD_MAIN, -71.5, 169, -349.5, -90, 0);

    public static final List<World> SKYBLOCK_WORLDS = ImmutableList.of(WORLD_SKYBLOCK, WORLD_SKYBLOCK_NETHER);

    public static final int SPAWN_HEIGHT = WORLD_MAIN.getSpawnLocation().getBlockY() - 20;
    public static final int VOID_HEIGHT = 50;

    public static final List<Npc> NPCS = ImmutableList.of(
        new VillagerHologramNpc(
            new Location(WORLD_MAIN, -51.5, 183, -340.5, 150, 0),
            BlockFace.NORTH,
            player -> player.performCommand("menu"),
            "§9§lMenü",
            Villager.Profession.BUTCHER.ordinal()
        )
    );

    public static final List<ItemStack> KIT_PLAYER = Arrays.asList(
        new ItemBuilder(Material.IRON_SWORD).enchant(Enchantment.DAMAGE_ALL, 3).build(),
        new ItemBuilder(Material.EXP_BOTTLE).amount(64).build(),
        new ItemBuilder(Material.EXP_BOTTLE).amount(64).build(),
        new ItemBuilder(Material.GOLDEN_APPLE).amount(3).build(),
        new ItemBuilder(Material.ENDER_PEARL).amount(2).build(),
        new ItemBuilder(Material.IRON_HELMET).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build(),
        new ItemBuilder(Material.IRON_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build(),
        new ItemBuilder(Material.IRON_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build(),
        new ItemBuilder(Material.IRON_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build()
    );

    public static final List<ItemStack> KIT_GOLD = Arrays.asList(
        new ItemBuilder(Material.DIAMOND_SWORD).enchant(Enchantment.DAMAGE_ALL, 2).build(),
        new ItemBuilder(Material.EXP_BOTTLE).amount(64).build(),
        new ItemBuilder(Material.EXP_BOTTLE).amount(64).build(),
        new ItemBuilder(Material.GOLDEN_APPLE).amount(4).build(),
        new ItemBuilder(Material.ENDER_PEARL).amount(3).build(),
        new ItemBuilder(Material.DIAMOND_HELMET).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build(),
        new ItemBuilder(Material.DIAMOND_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build(),
        new ItemBuilder(Material.DIAMOND_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build(),
        new ItemBuilder(Material.DIAMOND_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build()
    );

    public static final List<ItemStack> KIT_DIAMOND = Arrays.asList(
        new ItemBuilder(Material.DIAMOND_SWORD).enchant(Enchantment.DAMAGE_ALL, 3).build(),

        new ItemBuilder(Material.EXP_BOTTLE).amount(64).build(),
        new ItemBuilder(Material.EXP_BOTTLE).amount(64).build(),
        new ItemBuilder(Material.GOLDEN_APPLE).amount(5).build(),
        new ItemBuilder(Material.ENDER_PEARL).amount(4).build(),
        new ItemBuilder(Material.DIAMOND_HELMET).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build(),
        new ItemBuilder(Material.DIAMOND_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build(),
        new ItemBuilder(Material.DIAMOND_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build(),
        new ItemBuilder(Material.DIAMOND_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build()
    );

    public static final List<ItemStack> KIT_EMERALD = Arrays.asList(
        new ItemBuilder(Material.DIAMOND_SWORD).enchant(Enchantment.DAMAGE_ALL, 3).enchant(Enchantment.DURABILITY, 1).enchant(Enchantment.FIRE_ASPECT, 1).build(),
        new ItemBuilder(Material.EXP_BOTTLE).amount(64).build(),
        new ItemBuilder(Material.EXP_BOTTLE).amount(64).build(),
        new ItemBuilder(Material.GOLDEN_APPLE).amount(5).build(),
        new ItemBuilder(Material.ENDER_PEARL).amount(5).build(),
        new ItemBuilder(Material.DIAMOND_HELMET).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).enchant(Enchantment.DURABILITY, 1).build(),
        new ItemBuilder(Material.DIAMOND_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).enchant(Enchantment.DURABILITY, 1).build(),
        new ItemBuilder(Material.DIAMOND_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).enchant(Enchantment.DURABILITY, 1).build(),
        new ItemBuilder(Material.DIAMOND_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).enchant(Enchantment.DURABILITY, 1).build()
    );

    public static final List<ItemStack> KIT_OBSIDIAN = Arrays.asList(
        new ItemBuilder(Material.DIAMOND_SWORD).enchant(Enchantment.DURABILITY, 2).enchant(Enchantment.DAMAGE_ALL, 4).enchant(Enchantment.FIRE_ASPECT, 1).enchant(Enchantment.KNOCKBACK, 1).build(),
        new ItemBuilder(Material.BOW).enchant(Enchantment.ARROW_DAMAGE, 3).enchant(Enchantment.ARROW_KNOCKBACK, 1).build(),
        new ItemBuilder(Material.EXP_BOTTLE).amount(64).build(),
        new ItemBuilder(Material.EXP_BOTTLE).amount(64).build(),
        new ItemBuilder(Material.GOLDEN_APPLE).amount(5).build(),
        new ItemBuilder(Material.ENDER_PEARL).amount(6).build(),
        new ItemBuilder(Material.DIAMOND_HELMET).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).enchant(Enchantment.DURABILITY, 2).build(),
        new ItemBuilder(Material.DIAMOND_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).enchant(Enchantment.DURABILITY, 2).build(),
        new ItemBuilder(Material.DIAMOND_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).enchant(Enchantment.DURABILITY, 2).build(),
        new ItemBuilder(Material.DIAMOND_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).enchant(Enchantment.DURABILITY, 2).build()
    );

}
