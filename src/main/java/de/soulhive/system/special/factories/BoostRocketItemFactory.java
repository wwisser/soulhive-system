package de.soulhive.system.special.factories;

import de.soulhive.system.SoulHive;
import de.soulhive.system.special.SpecialItemFactory;
import de.soulhive.system.util.item.ItemBuilder;
import de.soulhive.system.util.nms.ParticleUtils;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.ThreadLocalRandom;

public class BoostRocketItemFactory implements SpecialItemFactory {

    private static final String TITLE = "§c§lRocketboost §7*Klick*";
    private static final Material MATERIAL = Material.FIREWORK;
    private static final long DELAY = 5000;
    private static final int COSTS = 1000;

    @Override
    public ItemStack createItem(final Player player) {
        return new ItemBuilder(MATERIAL)
            .name(TITLE)
            .modifyLore()
            .add("§f")
            .add("§7von " + player.getName())
            .finish()
            .build();
    }

    @Override
    public int getCosts() {
        return COSTS;
    }

    @Override
    public void execute(final Player player) {
        player.getPlayer().setVelocity(player.getVelocity().setY(1.0));
        player.playSound(player.getLocation(), Sound.FIREWORK_LAUNCH, 1, 1);

        new BukkitRunnable() {

            int ticks = 0;

            @Override
            public void run() {
                if (this.ticks >= 20) {
                    super.cancel();
                    return;
                }

                ParticleUtils.play(
                    player.getLocation(),
                    EnumParticle.FIREWORKS_SPARK,
                    0, 0, 0, 0, 0
                );

                this.ticks++;
            }
        }.runTaskTimer(SoulHive.getPlugin(), 0L, 1L);
    }

    @Override
    public boolean shouldExecute(final ItemStack itemStack, final Action action) {
        return (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)
            && itemStack.hasItemMeta()
            && itemStack.getItemMeta().hasDisplayName()
            && itemStack.getItemMeta().getDisplayName().equals(TITLE)
            && itemStack.getType() == MATERIAL;
    }

    @Override
    public String getUniqueName() {
        return "Rocketboost";
    }

    @Override
    public long getDelay() {
        return DELAY;
    }

}
