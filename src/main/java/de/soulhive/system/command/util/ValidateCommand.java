package de.soulhive.system.command.util;

import de.soulhive.system.SoulHive;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.exception.impl.*;
import de.soulhive.system.vanish.VanishService;
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

    /**
     * Must be a value over 0.
     */
    public int amount(String argument) throws InvalidAmountException {
        try {
            final int amount = Integer.parseInt(argument);

            if (amount > 0) {
                return amount;
            } else {
                throw new InvalidAmountException(argument);
            }
        } catch (NumberFormatException e) {
            throw new InvalidAmountException(argument);
        }
    }

    /**
     * Must be a positive value.
     */
    public int number(String argument) throws InvalidAmountException {
        try {
            final int amount = Integer.parseInt(argument);

            if (amount > -1) {
                return amount;
            } else {
                throw new InvalidAmountException(argument);
            }
        } catch (NumberFormatException e) {
            throw new InvalidAmountException(argument);
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

    public Player target(String target, CommandSender sender) throws CommandException {
        final VanishService vanishService = SoulHive.getServiceManager().getService(VanishService.class);
        final Player targetPlayer = Bukkit.getPlayer(target);

        if (targetPlayer == null
            || !targetPlayer.isOnline()
            || vanishService.getVanishedPlayers().contains(targetPlayer)) {
            throw new TargetNotFoundException(target);
        }

        if (targetPlayer == sender) {
            throw new SelfInteractionException();
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
