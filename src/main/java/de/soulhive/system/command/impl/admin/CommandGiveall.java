package de.soulhive.system.command.impl.admin;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.util.item.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandGiveall extends CommandExecutorWrapper {

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        ValidateCommand.permission(sender, Settings.PERMISSION_ADMIN);

        Player player = ValidateCommand.onlyPlayer(sender);
        ItemStack itemStack = ValidateCommand.heldItem(player);

        Bukkit.getOnlinePlayers()
            .stream()
            .filter(players -> players != player)
            .forEach(players -> {
                ItemUtils.addAndDropRest(players, itemStack);
                players.playSound(players.getLocation(), Sound.HORSE_SADDLE, 1, 1);
            });

        Bukkit.broadcastMessage(
            Settings.PREFIX
                + "Jeder Spieler hat "
                + itemStack.getAmount()
                + "x §f"
                + itemStack.getType().toString()
                + " §7erhalten"
        );
    }

}
