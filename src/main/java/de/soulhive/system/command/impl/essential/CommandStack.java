package de.soulhive.system.command.impl.essential;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.setting.Settings;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandStack extends CommandExecutorWrapper {

    private static final int MAX_STACK_SIZE = 64;

    @Override
    public void process(CommandSender sender, String[] args) throws CommandException {
        ValidateCommand.permission(sender, "soulhive.stack");
        Player player = ValidateCommand.onlyPlayer(sender);

        player.getInventory().setContents(this.stackContent(player.getInventory().getContents()));
        player.sendMessage(Settings.PREFIX + "Deine Items wurden §fgestackt§7!");
    }

    private ItemStack[] stackContent(ItemStack[] items) {
        int length = items.length;

        for (int i = 0; i < length; i++) {
            ItemStack item = items[i];

            if (item != null && item.getAmount() > 0) {
                if (item.getAmount() < MAX_STACK_SIZE) {
                    int needed = MAX_STACK_SIZE - item.getAmount();

                    for (int j = i + 1; j < length; j++) {
                        ItemStack item2 = items[j];

                        if (item2 != null && item2.getAmount() > 0) {
                            if ((item2.getTypeId() == item.getTypeId())
                                && (item.getDurability() == item2.getDurability())
                                && (((item.getItemMeta() == null)
                                && (item2.getItemMeta() == null))
                                || ((item.getItemMeta() != null)
                                && (item.getItemMeta().equals(item2.getItemMeta()))))) {
                                if (item2.getAmount() > needed) {
                                    item.setAmount(MAX_STACK_SIZE);
                                    item2.setAmount(item2.getAmount() - needed);
                                    break;
                                }
                                items[j] = null;
                                item.setAmount(item.getAmount() + item2.getAmount());
                                needed = MAX_STACK_SIZE - item.getAmount();
                            }
                        }
                    }
                }
            }
        }

        return items;
    }

}