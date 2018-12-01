package de.soulhive.system.command.impl.essential;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.util.item.ItemUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class CommandHat extends CommandExecutorWrapper {

    private static final List<String> FORBIDDEN_ITEMS = Arrays.asList("CHESTPLATE", "LEGGINGS", "BOOTS");

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        ValidateCommand.permission(sender, "soulhive.hat");
        Player player = ValidateCommand.onlyPlayer(sender);
        ItemStack itemStack = ValidateCommand.heldItem(player);

        if (FORBIDDEN_ITEMS
            .stream()
            .anyMatch(forbiddenItem -> itemStack.getType().toString().contains(forbiddenItem))) {
            player.sendMessage(Settings.PREFIX + "§cDu darfst keine Rüstung als Kopf verwenden.");
            return;
        }

        ItemStack helmet = player.getInventory().getHelmet();
        if (helmet != null && helmet.getType() != Material.AIR) {
            ItemUtils.addAndDropRest(player, helmet);
        }

        player.getInventory().setHelmet(player.getItemInHand().clone());
        player.setItemInHand(null);
        player.sendMessage(Settings.PREFIX + "§7Du hast dir §f" + itemStack.getType() + " §7aufgesetzt.");
    }

}
