package de.soulhive.system.service.micro;

import de.soulhive.system.SoulHive;
import de.soulhive.system.service.Service;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.user.User;
import de.soulhive.system.user.UserService;
import de.soulhive.system.util.item.ItemUtils;
import de.soulhive.system.util.nms.ActionBar;
import org.bukkit.Sound;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class FrameshopService extends Service implements Listener {

    private UserService userService;

    @Override
    public void initialize() {
        super.registerListener(this);
        this.userService = SoulHive.getServiceManager().getService(UserService.class);
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        final Player player = event.getPlayer();
        final Entity rightClicked = event.getRightClicked();

        if (!player.getWorld().equals(Settings.WORLD_MAIN)
            || player.getLocation().getBlockY() < Settings.SPAWN_HEIGHT
            || rightClicked.getType() != EntityType.ITEM_FRAME) {
            return;
        }

        event.setCancelled(true);
        final BlockState state = event.getRightClicked().getLocation().add(0, 1, 0).getBlock().getState();

        if (!(state instanceof Sign)) {
            return;
        }

        final Sign sign = (Sign) state;
        int amount = Integer.valueOf(sign.getLine(2).split(" ")[0]);
        int price = Integer.valueOf(sign.getLine(3).split(" ")[1]);
        ItemStack itemStack = ((ItemFrame) event.getRightClicked()).getItem();

        if (player.isSneaking() && amount == 1) {
            price = price * 64;
            amount = 64;
        }

        final User user = this.userService.getUser(player);

        if (sign.getLine(0).contains("Verkaufen")) {
            if (this.getAmount(player, itemStack) >= amount) {
                user.addJewels(price);
                this.removeItem(player, itemStack, amount);

                player.playSound(player.getLocation(), Sound.HORSE_SADDLE, 1, 1);
                player.sendMessage(Settings.PREFIX + "Du hast §d" + price + " Juwelen §7für den Verkauf erhalten.");
            } else {
                ActionBar.send("§cDir fehlen die benötigten Items.", player);
            }
        } else if (sign.getLine(0).contains("Kaufen")) {
            if (user.getJewels() >= price) {
                ItemStack item = itemStack.clone();

                item.setAmount(amount);
                user.removeJewels(price);

                ItemUtils.addAndDropRest(player, item);
                player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
            } else {
                ActionBar.send("§cDu hast zu wenige Juwelen.", player);
            }
        }
    }

    private int getAmount(Player player, ItemStack itemStack) {
        return Arrays.stream(player.getInventory().getContents())
            .filter(
                item -> item != null
                    && item.getType().equals(itemStack.getType())
                    && !item.hasItemMeta()
                    && item.getData().getData() == itemStack.getData().getData()
            ).mapToInt(ItemStack::getAmount)
            .sum();
    }

    private void removeItem(Player player, ItemStack itemStack, int amount) {
        player.getInventory().removeItem(new ItemStack(itemStack.getType(), amount, itemStack.getData().getData()));
        player.updateInventory();
    }

}
