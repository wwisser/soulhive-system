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

public class CommandBottle extends CommandExecutorWrapper {

    @Override
    public void process(CommandSender sender, String[] args) throws CommandException {
        ValidateCommand.permission(sender, "soulhive.bottle");
        Player player = ValidateCommand.onlyPlayer(sender);

        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack != null && itemStack.getType() == Material.GLASS) {
                itemStack.setType(Material.GLASS_BOTTLE);
            }
        }

        player.updateInventory();
        player.sendMessage(Settings.PREFIX + "Dein Glas wurde zu §fFlaschen§7!");
        player.playSound(player.getLocation(), Sound.GLASS, 1, 1);
    }

}
