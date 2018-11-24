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

public class CommandFill extends CommandExecutorWrapper {

    @Override
    public void process(CommandSender sender, String[] args) throws CommandException {
        ValidateCommand.permission(sender, "soulhive.fill");
        Player player = ValidateCommand.onlyPlayer(sender);

        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack != null && itemStack.getType() == Material.GLASS_BOTTLE) {
                itemStack.setType(Material.POTION);
            }
        }

        player.updateInventory();
        player.sendMessage(Settings.PREFIX + "Deine Flaschen wurden §fbefüllt§7!");
        player.playSound(player.getLocation(), Sound.DRINK, 1, 1);
    }

}
