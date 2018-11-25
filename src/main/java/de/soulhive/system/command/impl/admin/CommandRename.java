package de.soulhive.system.command.impl.admin;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.setting.Settings;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CommandRename extends CommandExecutorWrapper {

    private static final String USAGE = "/rename <name>";

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        ValidateCommand.permission(sender, Settings.PERMISSION_ADMIN);
        Player player = ValidateCommand.onlyPlayer(sender);

        ValidateCommand.minArgs(1, args, USAGE);
        ItemStack itemStack = ValidateCommand.heldItem(player);

        ItemMeta itemMeta = itemStack.getItemMeta();
        String name = "§f" + String.join(" ", args);

        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        itemStack.setItemMeta(itemMeta);
        sender.sendMessage(Settings.PREFIX + "Item zu '§f" + name + "§7' umbenannt.");
    }

}