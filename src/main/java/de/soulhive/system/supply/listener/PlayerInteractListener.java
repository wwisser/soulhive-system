package de.soulhive.system.supply.listener;

import de.soulhive.system.delay.DelayConfiguration;
import de.soulhive.system.delay.DelayService;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.supply.SupplyService;
import de.soulhive.system.util.nms.ActionBar;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class PlayerInteractListener implements Listener {

    private static final DelayConfiguration DELAY_CONFIGURATION = new DelayConfiguration(
        "§cWarte noch %time",
        1500
    );

    private SupplyService supplyService;
    private DelayService delayService;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();

        if (!player.getWorld().equals(Settings.WORLD_MAIN)) {
            return;
        }

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK
            && event.getClickedBlock().getState() instanceof Sign) {
            final Sign sign = (Sign) event.getClickedBlock().getState();

            if (!sign.getLine(0).equalsIgnoreCase("§5[KOSTENLOS]")) {
                return;
            }

            this.delayService.handleDelayInverted(
                player,
                DELAY_CONFIGURATION,
                delayedPlayer -> {
                    event.setCancelled(true);
                    player.playSound(player.getLocation(), Sound.CREEPER_HISS, 1, 1);
                    ActionBar.send("§cWarte noch einen Moment.", delayedPlayer);
                }
            );

            if (event.isCancelled()) {
                return;
            }

            int index = Integer.parseInt(sign.getLines()[1].split(" ")[1]);
            final ItemStack itemStack = this.supplyService.getItemStacks().get(index);
            final Inventory inventory = Bukkit.createInventory(
                null,
                3 * 9,
                sign.getLine(0) + " " + sign.getLine(3)
            );

            inventory.setItem(13, itemStack);
            player.openInventory(inventory);
            player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
        }
    }


}

