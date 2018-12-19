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

    public int amount(String argument) throws CommandException {
        try {
            final int amount = Integer.parseInt(argument);

            if (amount > 0) {
                return amount;
            } else {
                throw new CommandException("§c'" + argument + "' ist keine gültige Zahl.");
            }
        } catch (NumberFormatException e) {
            throw new CommandException("§c'" + argument + "' ist keine gültige Zahl.");
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
        Player targetPlayer = Bukkit.getPlayer(target);

        if (targetPlayer == null
            || !targetPlayer.isOnline()
            || vanishService.getVanishedPlayers().contains(targetPlayer)) {
            throw new TargetNotFoundException(target);
        }

        if (targetPlayer == sender) {
            throw new CommandException("§cDu darfst nicht mit dir selbst interagieren!");
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
