package de.soulhive.system.service.micro;

import de.soulhive.system.SoulHive;
import de.soulhive.system.service.FeatureService;
import de.soulhive.system.service.Service;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.user.User;
import de.soulhive.system.user.UserService;
import de.soulhive.system.util.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

@FeatureService
public class FirelotusService extends Service {

    private static final String BLOOM_NAME = "§6§lFeuerlotusblüte";
    private static final ItemStack BLOOM_ITEM = new ItemBuilder(Material.RED_ROSE)
        .data((byte) 5)
        .name(BLOOM_NAME)
        .enchant(Enchantment.LUCK, 6)
        .build();
    private static final int MAX_JEWEL_REWARD = 30;

    private UserService userService;

    @Override
    public void initialize() {
        this.userService = SoulHive.getServiceManager().getService(UserService.class);
    }

    @Override
    public void disable() {
        Settings.WORLD_MAIN.getEntities()
            .stream()
            .filter(entity -> entity instanceof Item)
            .filter(entity -> ((Item) entity).getItemStack().equals(BLOOM_ITEM))
            .forEach(Entity::remove);
    }

    private class PlayerPickupItemListener implements Listener {

        @EventHandler
        public void onPlayerPickupItem(PlayerPickupItemEvent event) {
            final ItemStack itemStack = event.getItem().getItemStack();
            final Player player = event.getPlayer();

            if (!itemStack.equals(BLOOM_ITEM)) {
                return;
            }

            event.setCancelled(true);
            event.getItem().remove();

            final User user = FirelotusService.this.userService.getUser(player);
            final int amount = ThreadLocalRandom.current().nextInt(MAX_JEWEL_REWARD) + 1;

            player.sendMessage(
                Settings.PREFIX + "Du hast eine §6Feuerlotusblüte §7gefunden. + §d" + amount + " Juwelen"
            );
            player.setHealth(20);
            player.setFoodLevel(20);
            player.playSound(player.getLocation(), Sound.NOTE_PLING, Float.MAX_VALUE, Float.MAX_VALUE);
            user.addJewels(amount);
        }

    }

}
