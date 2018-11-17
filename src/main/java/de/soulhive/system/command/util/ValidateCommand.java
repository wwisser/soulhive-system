package de.soulhive.system.command.util;

import de.soulhive.system.command.exception.impl.*;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@UtilityClass
public class ValidateCommand {

    public void minArgs(int min, String[] args, String commandUsage) throws InvalidArgsException {
        if (args.length < min) {
            throw new InvalidArgsException(commandUsage);
        }
    }

    public void permission(CommandSender sender, String permission) throws PermissionException {
        if (!sender.hasPermission(permission)) {
            throw new PermissionException();
        }
    }

    public Player onlyPlayer(CommandSender sender) throws InvalidSenderException {
        if (!(sender instanceof Player)) {
            throw new InvalidSenderException();
        }

        return (Player) sender;
    }

    public Player target(String target) throws TargetNotFoundException {
        Player targetPlayer = Bukkit.getPlayer(target);

        if (targetPlayer == null || !targetPlayer.isOnline()) {
            throw new TargetNotFoundException(target);
        }

        return targetPlayer;
    }

    public ItemStack heldItem(Player player) throws HeldItemNotFoundException {
        ItemStack itemInHand = player.getItemInHand();

        if (itemInHand == null || itemInHand.getType() == Material.AIR) {
            throw new HeldItemNotFoundException();
        }

        return itemInHand;
    }

}
