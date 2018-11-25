package de.soulhive.system.command.impl.essential;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.setting.Settings;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class CommandCook extends CommandExecutorWrapper {

    private static final List<Material> COOKABLE_ITEMS = Arrays.asList(
        Material.RAW_BEEF,
        Material.PORK,
        Material.RAW_CHICKEN,
        Material.POTATO_ITEM,
        Material.RABBIT,
        Material.MUTTON
    );

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        ValidateCommand.permission(sender, "soulhive.cook");
        Player player = ValidateCommand.onlyPlayer(sender);

        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack != null && COOKABLE_ITEMS.contains(itemStack.getType())) {
                itemStack.setTypeId(itemStack.getTypeId() + 1);
            }
        }

        player.updateInventory();
        player.sendMessage(Settings.PREFIX + "Alle essbaren Items wurden §fgekocht§7!");
        player.playSound(player.getLocation(), Sound.BURP, 1, 1);

    }

}
