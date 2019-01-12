package de.soulhive.system.service.micro;

import com.google.common.collect.ImmutableList;
import de.soulhive.system.SoulHive;
import de.soulhive.system.service.FeatureService;
import de.soulhive.system.service.Service;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.task.ComplexTask;
import de.soulhive.system.task.TaskService;
import de.soulhive.system.user.User;
import de.soulhive.system.user.UserService;
import de.soulhive.system.util.item.ItemBuilder;
import de.soulhive.system.util.nms.ParticleUtils;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Location;
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
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
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
    private static final List<Location> BLOOM_DROP_LOCATIONS = ImmutableList.of(
        new Location(Settings.WORLD_MAIN, -63.5, 90, -335.5),
        new Location(Settings.WORLD_MAIN, -59.5, 90, -339.5)
    );

    private UserService userService;

    @Override
    public void initialize() {
        this.userService = SoulHive.getServiceManager().getService(UserService.class);
        final TaskService taskService = SoulHive.getServiceManager().getService(TaskService.class);

        taskService.registerTasks(new FirelotusBloomDropTask());
    }

    @Override
    public void disable() {
        this.clearBloomDrops();
    }

    private void clearBloomDrops() {
        Settings.WORLD_MAIN.getEntities()
            .stream()
            .filter(entity -> entity instanceof Item)
            .filter(entity -> ((Item) entity).getItemStack().equals(BLOOM_ITEM))
            .forEach(Entity::remove);
    }

    private class FirelotusBloomDropTask extends BukkitRunnable implements ComplexTask {

        private static final long PERIOD = 20L * 30;

        @Override
        public void setup(JavaPlugin plugin) {
            this.runTaskTimer(plugin, 0, PERIOD);
        }

        @Override
        public void run() {
            if (Settings.WORLD_MAIN.getPlayers()
                .stream()
                .filter(Player::isOnline)
                .filter(player -> player.getLocation().getY() < Settings.SPAWN_HEIGHT).count() < 2) {
                return;
            }

            final ThreadLocalRandom random = ThreadLocalRandom.current();

            if (random.nextInt(100) < 5) {
                FirelotusService.this.clearBloomDrops();

                final Location location = BLOOM_DROP_LOCATIONS.get(
                    random.nextInt(BLOOM_DROP_LOCATIONS.size())
                ).clone().add(0, 0.3, 0);

                location.getWorld().dropItem(location, BLOOM_ITEM);
                ParticleUtils.play(location, EnumParticle.VILLAGER_ANGRY, 0, 0, 0, 0, 0);
            }
        }

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
