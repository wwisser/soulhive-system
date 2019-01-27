package de.soulhive.system.command.impl.essential;

import com.google.common.collect.ImmutableList;
import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.setting.Settings;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CommandRepair extends CommandExecutorWrapper {

    private static final List<Material> REPAIRABLE_ITEMS = ImmutableList.of(
        Material.DIAMOND_PICKAXE,
        Material.DIAMOND_SWORD,
        Material.DIAMOND_SPADE,
        Material.DIAMOND_AXE,
        Material.DIAMOND_HOE,
        Material.DIAMOND_HELMET,
        Material.DIAMOND_CHESTPLATE,
        Material.DIAMOND_LEGGINGS,
        Material.DIAMOND_BOOTS,
        Material.IRON_PICKAXE,
        Material.IRON_SWORD,
        Material.IRON_SPADE,
        Material.IRON_AXE,
        Material.IRON_HOE,
        Material.IRON_HELMET,
        Material.IRON_CHESTPLATE,
        Material.IRON_LEGGINGS,
        Material.IRON_BOOTS,
        Material.GOLD_PICKAXE,
        Material.GOLD_SWORD,
        Material.GOLD_SPADE,
        Material.GOLD_AXE,
        Material.GOLD_HOE,
        Material.GOLD_HELMET,
        Material.GOLD_CHESTPLATE,
        Material.GOLD_LEGGINGS,
        Material.GOLD_BOOTS,
        Material.STONE_PICKAXE,
        Material.STONE_SWORD,
        Material.STONE_SPADE,
        Material.STONE_AXE,
        Material.STONE_HOE,
        Material.CHAINMAIL_HELMET,
        Material.CHAINMAIL_CHESTPLATE,
        Material.CHAINMAIL_LEGGINGS,
        Material.CHAINMAIL_BOOTS,
        Material.WOOD_PICKAXE,
        Material.WOOD_SWORD,
        Material.WOOD_SPADE,
        Material.WOOD_AXE,
        Material.WOOD_HOE,
        Material.LEATHER_HELMET,
        Material.LEATHER_CHESTPLATE,
        Material.LEATHER_LEGGINGS,
        Material.LEATHER_BOOTS,
        Material.FLINT_AND_STEEL,
        Material.SHEARS,
        Material.BOW,
        Material.FISHING_ROD,
        Material.ANVIL
    );

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        Player player = ValidateCommand.onlyPlayer(sender);
        ValidateCommand.permission(sender, "soulhive.repair");

        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack != null && REPAIRABLE_ITEMS.contains(itemStack.getType())) {
                itemStack.setDurability((short) 0);
            }
        }

        player.sendMessage(Settings.PREFIX + "Deine Items wurden repariert.");
        player.playSound(player.getLocation(), Sound.ANVIL_USE, 1, 1);
    }

}
