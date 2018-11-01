package de.soulhive.system.command.impl.admin;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.setting.Settings;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandItem extends CommandExecutorWrapper {

    private static final String COMMAND_USAGE = "/item <ID:Value> <Anzahl>";

    @Override
    public void process(CommandSender sender, String[] args) throws CommandException {
        ValidateCommand.permission(sender, "soulhive.item");
        ValidateCommand.onlyPlayer(sender);
        ValidateCommand.minArgs(1, args, COMMAND_USAGE);

        Player player = (Player) sender;

        short data = args[0].contains(":") ? Short.valueOf(args[0].split(":")[1]) : 0;
        int amount = args.length == 2 ? Integer.valueOf(args[1]) : 1;
        int id = args[0].contains(":") ? Integer.valueOf(args[0].split(":")[0]) : Integer.valueOf(args[0]);
        ItemStack itemStack = new ItemStack(id, amount, data);

        player.getInventory().addItem(itemStack);
        player.sendMessage(
            Settings.PREFIX
                + "§7Du hast "
                + itemStack.getAmount()
                + "x §f"
                + itemStack.getType().toString()
                + " §7erhalten"
        );
    }

}